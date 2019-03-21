package com.zenika.ageofdevsecops.infrastructure.challenge.checker

import com.zenika.ageofdevsecops.domain.challenge.Player
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.springframework.stereotype.Component
import java.net.URL
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.time.LocalDate
import java.time.ZoneId
import javax.net.ssl.*


@Component
class SSLCertificateChecker: ChallengeChecker {

    init {
        val ctx = SSLContext.getInstance("TLS")
        ctx.init(arrayOfNulls<KeyManager>(0), arrayOf<TrustManager>(DefaultTrustManager()), SecureRandom())
        SSLContext.setDefault(ctx)
    }

    override fun check(player: Player): Boolean {
        return try {
            val destinationURL = URL("https://${player.main.url}")
            val conn = destinationURL.openConnection() as HttpsURLConnection
            conn.hostnameVerifier = NoopHostnameVerifier.INSTANCE
            conn.connect()
            val certs = conn.serverCertificates
            val cert = certs[0] as X509Certificate

            val expirationDate = cert.notAfter.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            expirationDate != LocalDate.parse("2019-03-19")
        } catch (e: Exception) {
            false
        }
    }

    private class DefaultTrustManager : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(arg0: Array<X509Certificate>, arg1: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(arg0: Array<X509Certificate>, arg1: String) {
        }
    }

}
