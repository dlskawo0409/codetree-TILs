import java.util.*;
import java.io.*;

class Knight{
	int num;
	int row;
	int col;
	int h;
	int w;
	int k;
	int damage = 0;
	boolean live = true;
	
	Knight(int num, int row, int col, int h, int w ,int k){
		this.num = num;
		this.row = row;
		this.col = col;
		this.h = h;
		this.w = w;
		this.k = k;
	}
	
	
}

class Solution{
	int L;
	int N;
	int Q;
	int Map[][];
	int knightMap[][]; // 기사가 곂치는 지 검사
	int rowAppend[] = {-1, 0, 1, 0}; //위 오른쪽 아래 왼쪽
	int colAppend[] = {0, 1, 0, -1};
	Queue<Knight> process = new LinkedList<>();
	Knight[] knightList;
	
	int answer = 0;
	public void solution() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		L = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		Map = new int[L+1][L+1];
		knightMap = new int[L+1][L+1];
		knightList = new Knight[N+1];
		
		for(int i = 1; i<=L; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j<=L ;j++) {
				int now = Integer.parseInt(st.nextToken());
				Map[i][j] = now;
			}
		}
		
		
		for(int n = 1; n<=N; n++) {
			st = new StringTokenizer(br.readLine());
			int row = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			
			knightList[n] = new Knight(n,row, col, h, w, k);
			
			for(int i = row; i<row+h; i++) {
				for(int j = col; j<col+w; j++) {
					knightMap[i][j] = n;
				}
			}
			
		}
//		printKnightMap();
		for(int q = 0; q<Q; q++) {
			st = new StringTokenizer(br.readLine());
			int knightNum = Integer.parseInt(st.nextToken());
			int direction = Integer.parseInt(st.nextToken());
//			System.out.println(knightNum);
			
			if(canMove(knightNum, direction)) {
				while(!process.isEmpty()) {
					Knight now = process.poll();
//					move(now, direction ,false);
					if(process.size() == 0)
						move(now, direction, true);
					else
						move(now, direction ,false);
				}
			}
			process = new LinkedList<>();
//			System.out.println();
		}
		for(int i = 1; i<=N; i++) {
			if(knightList[i].live) {
//				System.out.println(i +" damage is " +knightList[i].damage);
				answer += knightList[i].damage;
			}
		}
		System.out.println(answer);
		
		
	}
	
	protected boolean canMove(int knightNum, int direction) { // 
//		System.out.println(knightNum);
		Knight now = knightList[knightNum];
		
		if(!now.live) {
			return false;
		}
		
		int tempRow = now.row + rowAppend[direction]; 
		int tempCol = now.col + colAppend[direction];
		
		
		
		if(isInMap(tempRow, tempCol, now.h, now.w ) && isNotWall(tempRow, tempCol, now.h, now.w)) {// 벽이나 밖이면 움직일 수 있음 
//			System.out.println(knightNum + " is in Map");

				// 기사가 있는지 체크
				Set<Integer> nextKnight = getKnightNum(tempRow, tempCol, now.h, now.w, knightNum);
//				System.out.println("parent :" +knightNum +" direction : "+direction);
				if(nextKnight.size()!= 0) {
					Iterator<Integer> it = nextKnight.iterator();
//					int z = 0;
					while(it.hasNext()) {
//						z ++;
						int next = it.next();
//						System.out.println("child : "+next);
//						System.out.println(next);
						if(!canMove(next, direction)) { 
							return false;
						}
//						if(z == 1)
//							break;
					}
					process.add(now);
					return true;
				}

				process.add(now);
				return true;	
			}

		return false;
	}
	
	protected void move(Knight now, int direction, boolean last) {
		int tempRow = now.row + rowAppend[direction]; 
		int tempCol = now.col + colAppend[direction];
		int damage = knightDie(tempRow, tempCol, now.h, now.w);
		if(last) {

			changeKnightMap(now, tempRow, tempCol);
			now.row = tempRow;
			now.col = tempCol;
		}
		else if(damage>= now.k) {
			now.live = false;
			deleteKnight(now.row, now.col, now.h, now.w);
		}
		else {
			now.k -= damage;
			now.damage += damage;
			changeKnightMap(now, tempRow, tempCol);
			now.row = tempRow;
			now.col = tempCol;
		}
//		System.out.println(now.num);
//		printKnightMap();
	}
	
	protected boolean isInMap(int row, int col, int h, int w) {
		if(1<=row && row<=L && 1<= col && col <=L && 1<= row + h -1 &&  row + h - 1 <=L && 1 <= col + w -1 && col + w -1 <=L) {
			return true;
		}
		
		return false;
	}
	
	protected boolean isNotWall(int row, int col, int h, int w) {
		for(int i = row; i<row + h; i++) {
			for(int j = col; j<col + w; j++) {
				if(Map[i][j] == 2) { // 옮긴 위치가 벽이면
					return false;
				}
			}
		}
		return true;
	}
	
	protected Set <Integer>  getKnightNum(int row, int col, int h, int w, int idx) {
		Set <Integer> result = new HashSet<>();
		
		for(int i = row; i<row + h; i++) {
			for(int j = col; j<col + w; j++) {
				if( knightMap[i][j] != 0 && knightMap[i][j] != idx) { // 다름 기사가 존재한다면 
					result.add(knightMap[i][j]);
				}
			}
		}
		return result;
	}
	
	protected void deleteKnight(int row, int col, int h, int w) {
		for(int i = row; i<row + h; i++) {
			for(int j = col; j<col + w; j++) {
					knightMap[i][j] = 0;
				}
			}
		
	}
	
	
	protected int knightDie(int row, int col, int h, int w) {
		int result = 0;
		for(int i = row; i<row + h; i++) {
			for(int j = col; j<col + w; j++) {
				if(Map[i][j] == 1) {
					result ++;
				}
			}
		}
//		System.out.println("answer : "+answer);
		return result;
	}
	
	protected void changeKnightMap(Knight knight, int row, int col) {
		for(int i = knight.row; i<knight.row+knight.h; i++) {
			for(int j = knight.col; j<knight.col + knight.w; j++) {
				knightMap[i][j] = 0;
			}
		}
		for(int i = row; i<row+knight.h; i++) {
			for(int j = col; j<col + knight.w; j++) {
				knightMap[i][j] = knight.num;
			}
		}
	}
	
	protected void printKnightMap() {
		for(int i = 1; i<=L ; i++) {
			for(int j = 1; j<=L; j++) {
				System.out.print(knightMap[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
	
}

public class Main {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		new Solution().solution();
	}

}