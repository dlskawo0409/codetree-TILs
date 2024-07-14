import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

class ColorNode{
	int mid;
	ColorNode parent = null;
	LinkedList<ColorNode> child = new LinkedList<>();
	int color;
	int depth;
	
	ColorNode(int mid,  int color, int depth){
		this.mid = mid;
		this.color = color;
		this.depth = depth;
	}
	
	ColorNode(int mid, ColorNode pid, int color, int depth){
		this.mid = mid;
		this.parent = pid;
		this.color = color;
		this.depth = depth;
	}

}

public class Main {
	int Q = 0;
	HashMap<Integer, ColorNode> map = new HashMap<>();
	public void solution() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Q = Integer.parseInt(br.readLine());
		LinkedList<ColorNode> top = new LinkedList<>();
		
		for(int q = 0; q<Q; q++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int query = Integer.parseInt(st.nextToken());
			int mid = 0;
			int color = 0;
			switch(query){

			case 100:
				 mid = Integer.parseInt(st.nextToken());
				int pid = Integer.parseInt(st.nextToken());
				color = Integer.parseInt(st.nextToken());
				int depth = Integer.parseInt(st.nextToken());
				
				if(pid == -1) {
					ColorNode temp = new ColorNode(mid, color, depth);
					map.put(mid, temp);
					top.add(temp);
				}
				else {
					ColorNode temp = map.get(pid);
					if(checkMaxDepth(temp)) {
						ColorNode now = new ColorNode(mid, temp, color, depth);
						map.put(mid, now);
						map.get(pid).child.add(now);
					}
				}

				break;
			case 200:
				mid = Integer.parseInt(st.nextToken());
				color = Integer.parseInt(st.nextToken());
				changeColor(map.get(mid), color);
				
				break;
			case 300:
				mid = Integer.parseInt(st.nextToken());
				System.out.println(map.get(mid).color);
				
				break;
			case 400:
				int result = 0;
				for(ColorNode now : top) {
					boolean[] palte = new boolean[6];
					result += dfs(now, palte);	
				}
				System.out.println(result);

				
				break;
				
			}
			
		}
		
		
	}
	
	protected boolean checkMaxDepth(ColorNode temp){
		int tempDepth = 1;
		while(temp.parent != null) {
			if(temp.depth <= tempDepth) {
				return false;
			}
			temp = temp.parent;
			tempDepth ++;
		}
		return true;
	}
	
	protected void changeColor(ColorNode now ,int color) {
		now.color = color;
		for(ColorNode temp : now.child) {
			changeColor(temp, color);
		}
		
	}
	protected int dfs(ColorNode start, boolean[]palte) {
		if(start.child.size() == 0) {
			palte[start.color] = true;
			return 1;
		}
		int result = 0;
		boolean[] newPalte = new boolean[6];
		for(ColorNode now : start.child) {
			int temp = dfs(now, newPalte);
			result+= temp;
		}
		
		newPalte[start.color] = true;
		int my = 0;
		for(int i = 1; i<=5; i++) {
			if(newPalte[i]) {
				palte[i] = true;
				my++;
			}
		}
//		System.out.println("mid : "+start.mid+" my: "+my);
		result+= my*my;
		
		return result;
		
	}
	
	
	public static void main(String[] args) throws Exception{
		new Main().solution();
	}
}