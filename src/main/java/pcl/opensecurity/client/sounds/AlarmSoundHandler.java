package pcl.opensecurity.client.sounds;
//TODO Using the MachineSound from EnderIO for now, I'll rewrite this ASAP
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;

public class AlarmSoundHandler extends PositionedSound implements ITickableSound {
	  private boolean donePlaying;

	  public AlarmSoundHandler(ResourceLocation sound, float x, float y, float z, float volume, float pitch) {
	    super(sound);
	    this.xPosF = x;
	    this.yPosF = y;
	    this.zPosF = z;
	    this.volume = volume;
	    this.field_147663_c = pitch;
	    this.repeat = true;
	  }

	  @Override
	  public void update() {
	    ;
	  }

	  @Override
	  public boolean isDonePlaying() {
	    return donePlaying;
	  }

	  public void endPlaying() {
	    donePlaying = true;
	  }

	  public void startPlaying() {
	    donePlaying = false;
	  }
}
