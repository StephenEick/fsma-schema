// ----------------------------------------------------------------------------
// Copyright Kaleidoscope, Inc. or its affiliates. All Rights Reserved.
// ----------------------------------------------------------------------------
package com.safefood204.auth

import com.safefood204.logging.LoggerDelegate
import com.safefood204.util.ExpiredTokenException
import com.safefood204.util.InvalidTokenException
import com.safefood204.util.hours
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.security.SecureRandom
import java.time.Duration
import java.util.*
import java.util.function.Function

private const val USE_TEST_SIGNING_KEY = true // false for production
private const val PROP_signing_key = "kscope.jwt.signing-key"

// Used for token claims
internal const val FSMA_USER_ID = "fsma_user_id"
internal const val FOOD_BUSINESS_ID = "food_business_id"

@Service
class JwtService {

    private val logger by LoggerDelegate()

    @Value("\${$PROP_signing_key:}")
    var signingKeyB: String = ""

    private val signingKey: Key by lazy {
        if (USE_TEST_SIGNING_KEY) {
            logger.debug("***** USE_TEST_SIGNING_KEY - do not use in production *****")
            testSigningKey()
        } else
            productionSigningKey()
    }

    // Random String that changes each time the app runs
    private fun productionSigningKey(): Key {
        val secret = if (signingKeyB.isNotBlank())
            Decoders.BASE64.decode(signingKeyB)
        else
            ByteArray(64).apply {
                SecureRandom.getInstanceStrong().nextBytes(this)
                logger.warn("**** WARN: Random SigningKey: ${Encoders.BASE64.encode(this)}")
            }
        return Keys.hmacShaKeyFor(secret)
    }

    // ******** only for testing - does not use random key above ************
    private fun testSigningKey(): Key {
        val SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun extractUsername(token: String): String = extractClaim(token) { claims: Claims -> claims.subject }
    private fun extractFsmaUserId(token: String) = extractWhichId(FSMA_USER_ID, token)
    private fun extractFoodBusinessId(token: String) = extractWhichId(FOOD_BUSINESS_ID, token)

    fun getFoodBusinessIdAndFsmaUserId(token: String) = Pair(
        extractFoodBusinessId(token),
        extractFsmaUserId(token)
    )

    fun createExtraClaims(foodBusinessId: Long, fsaUserId: Long): MutableMap<String, Long> {
        val extraClaims = mutableMapOf<String, Long>()
        extraClaims[FOOD_BUSINESS_ID] = foodBusinessId
        extraClaims[FSMA_USER_ID] = fsaUserId
        return extraClaims
    }

    fun generateToken(
        userDetails: UserDetails,
        extraClaims: Map<String, Any> = HashMap(),
        expiresIn: Duration = 2.hours
    ): String {
        val nowMS = System.currentTimeMillis()
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(nowMS))
            .setExpiration(Date(nowMS + expiresIn.toMillis())) // Could make 2.hours configurable
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun extractWhichId(whichId: String, token: String): Long {
        return extractClaim(token) { claims: Claims ->
            when (val clientId = claims[whichId]) {
                is Int -> clientId.toLong()
                is Long -> clientId
                else -> throw Exception("$whichId claim not found")
            }
        }
    }

    private fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date =
        extractClaim(token) { obj: Claims -> obj.expiration }

    private fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims =
        try {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token.trim())
                .body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException("Expired Token", e)
        } catch (e: DecodingException) {
            throw InvalidTokenException("Invalid Token Decoding Error", e)
        } catch (e: IllegalArgumentException) {
            throw InvalidTokenException("JWT Token Error", e)
        }
}
