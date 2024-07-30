import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;



class Product implements Comparable<Product>{
	int id;
	int revenue;
	int dest;
	int gain = revenue -100;
	
	Product(int id, int revenue, int dest, int gain){
		this.id = id;
		this.revenue = revenue;
		this.dest = dest;
		this.gain = gain;
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
	PriorityQueue<Product> products = new PriorityQueue<>();
	int N;
	int M;
	int S = 0;
	boolean[] removed = new boolean[300001];
	int[] D = new int[2000];
	
	public void solution() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Q = Integer.parseInt(br.readLine());
		
		for(int q = 0; q<Q; q++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int query = Integer.parseInt(st.nextToken());
			
			
			switch(query) {
				case 100:
					N = Integer.parseInt(st.nextToken());
					M = Integer.parseInt(st.nextToken());
					initMap();
					
					for(int m = 0; m<M; m++) {
						int v = Integer.parseInt(st.nextToken());
						int u = Integer.parseInt(st.nextToken());
						int w = Integer.parseInt(st.nextToken());
//						System.out.println(v+" "+u+" "+w);
						Map[v][u] = Math.min(Map[v][u], w);
						Map[u][v] = Math.min(Map[u][v], w);
						
//						for(int i = 0; i<N; i++) {
//							Map[i][v] = Math.min(Map[i][v], Map[i][u]+w);
//							Map[i][u] = Math.min(Map[i][u], Map[i][v]+w);
//						}
						
					}
					getMinMap();
				
					break;
					
				case 200:
					int id = Integer.parseInt(st.nextToken());
					int revenue = Integer.parseInt(st.nextToken());
					int dest = Integer.parseInt(st.nextToken());
					products.add(new Product(id, revenue, dest, revenue - D[dest]));
					removed[id] = false;
					
					break;
				case 300:
					int remove = Integer.parseInt(st.nextToken());
					removed[remove] = true;
					
					break;
				case 400:
					System.out.println(sellPackage());
					break;
				case 500:
					S = Integer.parseInt(st.nextToken());
					getMinMap();
					ArrayList<Product> temp = new ArrayList<>();
					while(!products.isEmpty()) {
						Product ptemp = products.poll();
						if(!removed[ptemp.id])
							temp.add(ptemp);
					}
					
					for(int i = 0; i< temp.size(); i++) {
						Product ptemp = temp.get(i);
						ptemp.gain = ptemp.revenue - D[ptemp.dest];
						products.add(ptemp);
					}
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
	
	
	protected void getMinMap() {
		boolean[] visit = new boolean[N];
        Arrays.fill(D, inf);
        D[S] = 0;

        for (int i = 0; i < N - 1; i++) {
            int v = 0, minDist = inf;
            for (int j = 0; j < N; j++) {
                if (!visit[j] && minDist > D[j]) {
                    v = j;
                    minDist = D[j];
                }
            }
            visit[v] = true;
            for (int j = 0; j < N; j++) {
                if (!visit[j] && D[v] != inf && Map[v][j] != inf && D[j] > D[v] + Map[v][j]) {
                    D[j] = D[v] + Map[v][j];
                }
            }
        }
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
	
    protected int sellPackage() {
        while (!products.isEmpty()) {
            Product p = products.peek();
            // 최적이라고 생각한 여행 상품이 판매 불가능 하다면 while문을 빠져나가 -1을 반환합니다.
            if (p.gain < 0) {
                break;
            }
            products.poll();
            if (!removed[p.id]) {
                return p.id; // 해당 여행 상품이 취소되지 않았다면 정상 판매되므로 id를 반환합니다
            }
        }
        return -1;
    }
	
	public static void main(String[]args) throws Exception{
		new Main().solution();
	}
}