/*
 * Copyright 2016 Dan Fredell.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.azelart.artnetstack.domain.artpollreply;

/**
 * ArtPolLReply port status.
 * Fields 22 of ArtPollReply in the ArtNet specs
 *
 * @author Dan Fredell
 * @see <a href="http://www.artisticlicence.com/WebSiteMaster/User%20Guides/art-net.pdf">art-net.pdf</a>
 */
public class ArtPollReplyOutputPortStatus {

    /**
     * true if you can send ArtNet data to this port
     * and the node will transmit it to DMX
     */
    public boolean dataTransmitted;
    public boolean testPacket,
            sip,
            texPacket,
            mergingData,
            outputShort,
            ltp,
            unused;

    public ArtPollReplyOutputPortStatus(byte inputByte) {
        this.dataTransmitted = (inputByte & 0x80) != 0;
        this.testPacket = (inputByte & 0x40) != 0;
        this.sip = (inputByte & 0x20) != 0;
        this.texPacket = (inputByte & 0x10) != 0;
        this.mergingData = (inputByte & 0x08) != 0;
        this.outputShort = (inputByte & 0x04) != 0;
        this.ltp = (inputByte & 0x02) != 0;
        this.unused = (inputByte & 0x01) != 0;
    }
}
