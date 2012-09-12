ArtNetStack
===========

WARNING : Unstable version

Art-Net by Artistic Licence allows for broadcasting DMX data via IP/UDP.
This library is implementing the basic protocol for Java applications.

http://www.artisticlicence.com/WebSiteMaster/User%20Guides/art-net.pdf

Currently supported core features are:
  * **ArtPoll** Encode and Decode ArtPoll Packet
  * **ArtPollReply** Encode ArtPollReply
  * **ArtTimeCode** Encode and Decode ArtTimeCode
  * **ArtDMX** Decode ArtDMX

Currently supported events features are:
  * **Art** ArtNet packet event catch
  * **ArtPoll** ArtPoll event catch
  * **ArtPollReply** ArtPollReply event catch
  * **ArtTimeCode** ArtTimeCode event catch
  * **ArtDMX** ArtDMX event catch

  * **onTerminate** Server is die and disconnected
  * **onConnect** Server has binding address

Currently supported protocols are:
  * **Broadcast** Broadcast protocol (100%)
  * **Unicast** Unicast protocol (0%)