package io.iohk.atala.pollux.vc.jwt

import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.crypto.bc.BouncyCastleProviderSingleton
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.gen.ECKeyGenerator
import io.circe.Json
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers.mustBe
import org.scalatest.matchers.should.Matchers

import java.security.PrivateKey
import java.security.interfaces.ECPrivateKey

class ECDSAVerifierTest extends AnyFunSuite with Matchers {

  test("toECDSAVerifier should use BouncyCastleProviderSingleton") {
    val ecKey = ECKeyGenerator(Curve.SECP256K1).provider(BouncyCastleProviderSingleton.getInstance()).generate()
    val verifier = JWTVerification.toECDSAVerifier(ecKey.toPublicKey)
    val provider = verifier.getJCAContext.getProvider
    provider mustBe a[BouncyCastleProvider]
  }
}
