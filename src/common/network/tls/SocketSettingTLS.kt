package common.network.tls

data class SocketSettingTLS (
    val version: String = "TLS",
    val serverCertificate: String? = null,
    val clientCertificate: String? = null,
    val clientCertificateKey: String? = null,
    val clientCertificatePassword: String? = null,
    val checkServerCertificate: Boolean = true
)
