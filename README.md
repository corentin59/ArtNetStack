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

Currently supported events features are:
  * **ArtPoll** ArtPoll event catch
  * **ArtPollReply** ArtPollReply event catch
  * **ArtTimeCode** ArtTimeCode event catch

  * **onConnect** Server has binding adress

Currently supported protocols are:
  * **Broadcast** Broadcast protocol (100%)
  * **Unicast** Unicast protocol (0%)