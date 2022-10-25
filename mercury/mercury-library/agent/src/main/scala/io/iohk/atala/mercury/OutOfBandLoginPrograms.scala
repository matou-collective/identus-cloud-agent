package io.iohk.atala.mercury

import io.iohk.atala.mercury.model._
import io.iohk.atala.mercury.protocol.outofbandlogin._

object OutOfBandLoginPrograms {

  def generateLoginInvitation(did: DidId) = {
    val invitation = OutOfBandLoginInvitation(from = did)
    Message(
      piuri = invitation.`type`,
      from = Some(invitation.from),
      to = None,
      id = invitation.id,
    )
  }

  // def replyToLoginInvitation(replier: DidId, invitation: Invitation) = {
  //   val requestMediation = MediateRequest()
  //   Message(
  //     from = replier,
  //     to = DidId(invitation.from),
  //     id = requestMediation.id,
  //     piuri = requestMediation.`type`
  //   )
  // }
}