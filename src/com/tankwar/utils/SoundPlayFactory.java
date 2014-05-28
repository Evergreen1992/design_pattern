package com.tankwar.utils;

//import java.io.FileInputStream;
//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

/**
 * sound play utility
 * @author Evergreen
 *
 */
public class SoundPlayFactory {
	
	public static void getSoundByName(String src){
		/*switch(src){
		case "heroFire":
			soundPlay("source/sound/fire_shell.wav");
			break;
		case "enemyFire": 
			soundPlay("");;
			break;
		case "move": 
			//soundPlay("source/sound/engine_loop.wav");;
			break;
		default:
			break;
		}*/
	}
	
	public static void soundPlay(String soundUrl){
		/*FileInputStream fileau;
		try {
			fileau = new FileInputStream(soundUrl);
			AudioStream as=new AudioStream(fileau);  
	        AudioPlayer.player.start(as);  
		} catch (Exception e) {
			e.printStackTrace();
		}  */
	}

	public static void main(String[] args) throws Exception{
		soundPlay("source/sound/fire_shell.wav");
	}
}
