/**
 * 
 */
package fr.azelart.artnetstack.tools.timecode;

import java.io.IOException;
import java.util.TimerTask;

import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCode;
import fr.azelart.artnetstack.domain.arttimecode.ArtTimeCodeType;
import fr.azelart.artnetstack.server.ArtNetServer;
import fr.azelart.artnetstack.utils.ArtNetPacketEncoder;

/**
 * Timecode task.
 * @author Corentin Azelart.
 *
 */
public class TimeCodeTask extends TimerTask {

	/** An ArtNetServer. */
	private ArtNetServer artNetServer;
	
	/** ArtTimeCode. */
	private ArtTimeCode artTimeCode;
	
	public TimeCodeTask( ArtNetServer artNetServer, ArtTimeCodeType artTimeCodeType ) {
		this.artNetServer = artNetServer;
		this.artTimeCode = new ArtTimeCode();
		this.artTimeCode.setArtTimeCodeType( artTimeCodeType );
		this.artTimeCode.setFrameTime( 0 );
		this.artTimeCode.setSeconds( 0 );
		this.artTimeCode.setMinutes( 0 );
		this.artTimeCode.setHours( 0 );
	}

	/**
	 * Tick.
	 */
	@Override
	public void run() {
		if ( artNetServer != null ) {
			
			// +1
			this.artTimeCode.setSeconds(this.artTimeCode.getSeconds() + 1);
			
			if ( this.artTimeCode.getSeconds() == 60 ) {
				this.artTimeCode.setSeconds(0);
				this.artTimeCode.setMinutes(this.artTimeCode.getMinutes()+1);
			}
			if ( this.artTimeCode.getMinutes() == 60 ) {
				this.artTimeCode.setMinutes(0);
				this.artTimeCode.setHours( this.artTimeCode.getHours() + 1 );
			}
			
			try {
				artNetServer.sendPacket( ArtNetPacketEncoder.encodeArtTimeCodePacket( this.artTimeCode ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
