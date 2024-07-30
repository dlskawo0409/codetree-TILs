import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

//class Node{
//	int w;
//	int id;
//	int cost = 0;
//	
//	Node(int id){
//
//		this.id = id;
//	}
//	
////	@Override 
////	public int compareTo(Node o) {
////		return this.gain == o.gain ? this.id - o.id : Integer.compare(this.gain, o.gain)*-1 ;
////	}
//}

class Product implements Comparable<Product>{
	int id;
	int revenue;
	int dest;
	int gain = revenue -100;
	
	Product(int id, int revenue, int dest){
		this.id = id;
		this.revenue = revenue;
		this.dest = dest;
	}
	
	@Override
	public int compareTo(Product o) {
		return o.gain ==  this.gain ? this.id - o.id : Integer.compare(this.gain, o.gain)*-1;
	}
	
}

public class Main {
	int Q;
	int inf = 101;
	int[][]Map;
	ArrayList<Product> products = new ArrayList<>();
	int N;
	int M;
	int S = 0;
	public void solution() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Q = Integer.parseInt(br.readLine());
		
		for(int q = 0; q<Q; q++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int query = Integer.parseInt(st.nextToken());
			boolean first = true;
			
			switch(query) {
				case 100:
					N = Integer.parseInt(st.nextToken());
					M = Integer.parseInt(st.nextToken());
					initMap();
//					initNode();
					for(int m = 0; m<M; m++) {
						int v = Integer.parseInt(st.nextToken());
						int u = Integer.parseInt(st.nextToken());
						int w = Integer.parseInt(st.nextToken());
						
						Map[v][u] = Math.min(Map[v][u], w);
						Map[u][v] = Math.min(Map[u][v], w);
						
						for(int i = 0; i<N; i++) {
							Map[i][v] = Math.min(Map[i][v], Map[i][u]+w);
							Map[i][u] = Math.min(Map[i][u], Map[i][v]+w);
						}
						
					}
					getMinCost();
					break;
					
				case 200:
					int id = Integer.parseInt(st.nextToken());
					int revenue = Integer.parseInt(st.nextToken());
					int dest = Integer.parseInt(st.nextToken());
					products.add(new Product(id, revenue, dest));
					
					break;
				case 300:
					int remove = Integer.parseInt(st.nextToken());
					for(int i = 0; i<products.size(); i++) {
						if(products.get(i).id == remove) {
							products.remove(i);
							break;
						}
					}
					
					break;
				case 400:
//					if(first) {
//						getMinCost();
//						first = false;
//					}
					for(int i = 0; i<products.size(); i++) {
						Product now = products.get(i);
						now.gain = now.revenue - Map[S][now.dest];
//						System.out.println(now.id+" : "+now.gain);
					}
					
					Collections.sort(products);
					Product now = null;
					if(products.size()>0) {
						now = products.get(0);
						if(now.gain <0) {
							System.out.println(-1);
						}
						else {
							System.out.println(now.id);
							products.remove(0);
						}
						
					}
					else {
						System.out.println(-1);
					}
				

					
					break;
				case 500:
					S = Integer.parseInt(st.nextToken());
					
					break;
			}
		}
	}
	
	protected void initMap() {
		Map = new int[N][N];
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				Map[i][j] = inf;
			}
			Map[i][i] = 0;
		}
	
	}
	
//	protected void initNode() {
//		nodeList = new Node[N];
//		for(int i = 0; i<N; i++) {
//			nodeList[i] = new Node(i);
//		}
//	}
	
	protected void getMinCost() {

		
		
	}
	
	protected void printMap() {
		System.out.println();
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				System.out.print(Map[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[]args) throws Exception{
		new Main().solution();
	}
}