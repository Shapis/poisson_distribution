import java.awt.Frame;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;


public class Main extends GLCanvas implements GLEventListener, MouseWheelListener  {
	
	private static final int height = 700;
	private static final int width = height;
	static double factor = 6;
	static int myMatrixSize = 10*(int)factor;
	static  double[] myCapacitor = {4*factor,6*factor,4*factor,5*factor};
	static double[][] myMatrix = new double[myMatrixSize][myMatrixSize];
	static ArrayList<Double> returnList = new ArrayList<Double>();

	public static void main(String[] args) {
		

		
		for (int i = 0 ; i < myMatrixSize ; i++) {
			for (int j = 0 ; j < myMatrixSize ; j++) {
				if (i>myCapacitor[0]&&i<myCapacitor[1]&&j==myCapacitor[2]){
					myMatrix[i][j] = 200;
				}else if (i>myCapacitor[0]&&i<myCapacitor[1]&&j==myCapacitor[3]){
					myMatrix[i][j] = -200;
				} else {
				myMatrix[i][j] = 0;
				}
			}
		}
		
		
			
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities capabilities = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(capabilities);
        
        
        Frame frame = new Frame("AWT Window Test");
        frame.setSize(height, width);
        frame.add(canvas);
        frame.setVisible(true);

        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }});
        
        canvas.addGLEventListener(new Main());
        canvas.addMouseWheelListener(new Main());
        Animator animator = new Animator(canvas);
        animator.start();
		
		
		
		
		
		

	}
	
	private void executeAlgorithm(){
		for (int i = 1 ; i < myMatrixSize-1 ; i++) {
			for (int j = 1 ; j < myMatrixSize-1 ; j++) {
				
				if ((i>myCapacitor[0]&&i<myCapacitor[1]&&j==myCapacitor[2])||i>myCapacitor[0]&&i<myCapacitor[1]&&j==myCapacitor[3]){
					
				}else {
					myMatrix[i][j] = ((myMatrix[i-1][j-1]) + myMatrix[i-1][j+1] + myMatrix[i+1][j-1] + myMatrix[i+1][j+1])/4;
				}
				
			}
			}
	}

	private void update() {
		//System.out.println(currentTime);
		executeAlgorithm();
		
	}

	private void drawCircle (GL2 gl,double positionX, double positionY, double screenHeight , double screenWidth, double potential) {
		float red = 1;
		float green = 1;
		float blue = 1;
		
		float n = 20;
		
		if (potential>=0) {
			green = green - ((float)potential)/n;
			blue = blue - ((float)potential)/n;
		}else {
			green = green + ((float)potential)/n;
			red = red + ((float)potential)/n;
		}
		
		/*if (potential == 200 || potential == -200) {
			red = 0;
			green = 1;
			blue = 0;
		}*/
		
		
		
		gl.glBegin(GL.GL_POINTS);
		gl.glColor3f(red, green, blue);
	    for(int i =0; i <= 30; i++){
	    double angle = 2 * Math.PI * i / 30;
	    double x = Math.cos(angle);
	    double y = Math.sin(angle);
	    for (double j = 1 ; j >= 0.1 ; j = j - 0.02) {
	    gl.glVertex2d(positionX+((x*0.025)*j),positionY+((y*0.025)*j));
	    }
	    }
	}

	private void render(GLAutoDrawable drawable) {
		
	    GL2 gl =  drawable.getGL().getGL2();
	    
		    gl = drawable.getGL().getGL2();
		    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		    
		    	for (double i = 0 ; i < (10*factor) ; i++) {
		    		for (double j = 0 ; j < (10*factor) ; j++) {
		    		drawCircle (gl,((i-(5*factor))/(5*factor))+(1/(10*factor)),((j-(5*factor))/((5*factor)))+(1/(10*factor)), height, width,myMatrix[(int)i][(int)j]);
		    		}
		    	}
	    gl.glEnd();
	    try {
			Thread.sleep(100);
			}catch(InterruptedException ex) {
				
			}
	
	}

	public void display(GLAutoDrawable drawable) {
		 	update();
		    render(drawable);
		    
	}

	public void init(GLAutoDrawable drawable) {
	    // put your OpenGL initialization code here
		GLU glu = new GLU();
		 drawable.getGL().setSwapInterval(1);
		 
	}

	public void dispose(GLAutoDrawable drawable) {
	    // put your cleanup code here
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL gl = drawable.getGL();
	    gl.glViewport(0, 0, width, height);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
