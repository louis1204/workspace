package com.me.tutorial;
 
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
//import com.badlogic.gdx.utils.Json.Serializable;
import java.io.Serializable;
import com.badlogic.gdx.utils.JsonValue;
 
public class Player implements Serializable {
 
        private static final long serialVersionUID = 1L;
        Vector2 position;
        String textureLoc;   
        private static final int col = 4;
        private static final int row = 4;
        private int locationx;
    	private int locationy;
    	
    	Animation animation;
    	Texture playerTexture;
    	TextureRegion[] frames;
    	TextureRegion currentFrame;
    	float stateTime;
    	
    	
 
        public Player(Vector2 position, String textureLoc){
                this.position = position;
        playerTexture = new Texture(Gdx.files.internal("pokemon.png"));
        TextureRegion [][] tmp = TextureRegion.split(playerTexture, playerTexture.getWidth() / col, playerTexture.getHeight() / row);
        frames = new TextureRegion [col*row];
        
        int index = 0;
        for(int i = 0; i < row;i++){
        	for(int j = 0; j < col; j++){
        		frames[index++] = tmp [i][j];
        	}
        }
        
        animation = new Animation(2,frames);
        stateTime = 0f;
        currentFrame = animation.getKeyFrame(0);
        
        }
       
        

		public void update(){
			
			if(stateTime < 4) {
                stateTime += Gdx.graphics.getDeltaTime();
			}else{
                stateTime = 0;
			}
			
        	
        	if(Gdx.input.isTouched()){
    			
        		System.out.println("Touched!");
    			locationx = -15+Gdx.input.getX();
    			locationy = 700-Gdx.input.getY();
        	
    			if(position.x <= locationx){
    			position.x += 2f;
    			currentFrame = animation.getKeyFrame(8 + stateTime);
        		} 
    			if(position.x >=locationx){
    			position.x -= 2f;
    			currentFrame = animation.getKeyFrame(4 + stateTime);
        		} 
        		if(position.y<=locationy){
        			position.y += 2f;
        			currentFrame = animation.getKeyFrame(12 + stateTime);	
        		} 
        		if(position.y>=locationy){
        			position.y -= 2f;
        			currentFrame = animation.getKeyFrame(0 + stateTime);
        		} 
        	}
                //wsad input
                if (Gdx.input.isKeyPressed(Keys.W)){
                        position.y +=  2f;
                        currentFrame = animation.getKeyFrame(12 + stateTime);
                }
                if (Gdx.input.isKeyPressed(Keys.A)){
                        position.x -=  2f;
                        currentFrame = animation.getKeyFrame(4 + stateTime);
                }
                if (Gdx.input.isKeyPressed(Keys.D)){
                        position.x +=  2f;
                        currentFrame = animation.getKeyFrame(8 + stateTime);
                }
                if (Gdx.input.isKeyPressed(Keys.S)){
                        position.y -=  2f;
                        currentFrame = animation.getKeyFrame(0  + stateTime);
                }
               
                
                       
                
               
                //Accelerometer input
               
               
               
/*          float accelX = Gdx.input.getAccelerometerX();
                float accelY = Gdx.input.getAcceleromete java.io.Serializable;rY();
               
               
                if (accelX > -1){
                        position.y -=  3f;                     
                }              
                if (accelX < +1){
                        position.y +=  3f;                     
                }
               
                if Texture(accelY > -1){
                        position.x +=  3f;                     
                }
                if (accelY < +1){
                        position.x -=  3f;                     
                }
                */
               
                float accelX = Gdx.input.getAccelerometerX();
                float accelY = Gdx.input.getAccelerometerY();
               
                if (accelX < -1){
                        position.y -=  accelX;                 
                }              
                if (accelX > +1){
                        position.y -=  accelX;                 
                }
               
                if (accelY < -1){
                        position.x +=  accelY;                 
                }
                if (accelY > +1){
                        position.x +=  accelY;                 
                }
               
                 
                if (accelX > 3){
                 System.out.println("x axis is " + accelX);
                }
               
                if (accelX < -3){
                         System.out.println("x axis is " + accelX);
                        }
               
                if (accelY > 3){
                         System.out.println("y axis is " + accelX);
                        }
               
                if (accelY < -3){
                         System.out.println("y axis is " + accelX);
                        }
                 
                //System.out.println("y axis is " + accelY);
                //position.x = Gdx.input.getX();
                //position.y = Gdx.input.getY();
        }
       
        public static void savePlayer(Player playerPosition) throws IOException {
                FileHandle file = Gdx.files.local("player.dat");
                OutputStream out = null;
                try {
                        file.writeBytes(serialize(playerPosition.getPosition()), false);
                }catch(Exception ex){
                        System.out.println(ex.toString());
                }finally{
                        if(out != null) try{out.close();} catch (Exception ex){}
                }
               
                System.out.println("Saving Player");
        }
       
        public static Vector2 readPlayer() throws IOException, ClassNotFoundException {
                Vector2 playerPosition = null;
                FileHandle file = Gdx.files.local("player.dat");
                playerPosition = (Vector2) deserialize(file.readBytes());
                return playerPosition;
        }
       
        @SuppressWarnings("unused")
        private static byte[] serialize(Object obj) throws IOException {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ObjectOutputStream o = new ObjectOutputStream(b);
                o.writeObject(obj);
                return b.toByteArray();
        }
       
        public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
                ByteArrayInputStream b = new ByteArrayInputStream(bytes);
                ObjectInputStream o = new ObjectInputStream(b);
                return o.readObject();
        }
 
        public  Vector2 getPosition() {
                // TODO Auto-generated method stub
        		return position;
        }

	
		public void setPosition(Vector2 position) {
            this.position = position;
    
		}
		public Animation getAnimation() {
			return animation;
		}

		public void setAnimation(Animation animation) {
			this.animation = animation;
		}

		public TextureRegion[] getFrames() {
			return frames;
		}

		public void setFrames(TextureRegion[] frames) {
			this.frames = frames;
		}

		public TextureRegion getCurrentFrame() {
			return currentFrame;
		}

		public void setCurrentFrame(TextureRegion currentFrame) {
			this.currentFrame = currentFrame;
		}

		public float getStateTime() {
			return stateTime;
		}

		public void setStateTime(float stateTime) {
			this.stateTime = stateTime;
		}
		
}
		
		
