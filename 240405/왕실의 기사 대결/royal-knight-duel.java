import java.util.*;
import java.io.*;

class Knight{
	int row;
	int col;
	int h;
	int w;
	int k;
	boolean live = true;
	
	Knight(int row, int col, int h, int w ,int k){
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
			
			knightList[n] = new Knight(row, col, h, w, k);
			
			for(int i = row; i<row+h; i++) {
				for(int j = col; j<col+w; j++) {
					knightMap[i][j] = n;
				}
			}
			
		}
		
		for(int q = 0; q<Q; q++) {
			st = new StringTokenizer(br.readLine());
			int knightNum = Integer.parseInt(st.nextToken());
			int direction = Integer.parseInt(st.nextToken());
//			System.out.println(knightNum);
			move(knightNum, direction, false);
		}
		System.out.println(answer);
		
		
	}
	
	protected boolean move(int knightNum, int direction, boolean pushed) { // 
//		System.out.println(knightNum);
		Knight now = knightList[knightNum];
		
		if(!now.live) {
			return false;
		}
		
		int tempRow = now.row + rowAppend[direction]; 
		int tempCol = now.col + colAppend[direction];
		
		
		
		if(isInMap(tempRow, tempCol, now.h, now.w )) {
//			System.out.println(knightNum + " is in Map");
			if(isNotWall(tempRow, tempCol, now.h, now.w)) {  // 벽이 아니면 움직일 수 있음 
				// 기사가 있는지 체크
				Set<Integer> nextKnight = getKnightNum(tempRow, tempCol, now.h, now.w, knightNum);
				if(nextKnight.size()!= 0) {
					Iterator<Integer> it = nextKnight.iterator();
//					int z = 0;
					while(it.hasNext()) {
//						z ++;
						
						if(!move(it.next(), direction, true)) { 
							return false;
						}
//						if(z == 1)
//							break;
					}
		
				}
				if(pushed) { // 밀렸다면 
					//데미지 체크
					int tempK = knightDie(tempRow, tempCol, now.h, now.w, now.k);
					if(tempK == 0) { // 죽으면
						now.live = false;
						deleteKnight(now.row, now.col, now.h, now.w);
						return true;
					}
					else {
						now.k = tempK;
						now.row = tempRow;
						now.col = tempCol;
						return true;
					}
					
				}
				// 그냥 혼자 명령에 따라 움직임
					
				now.row = tempRow;
				now.col = tempCol;


			}
			else { // 움직일 곳에 벽이 있어서 못 움직임
				return false;
			};

		}
		else { // 밖으로 나가버림
			System.out.println(knightNum+" die by out of map");
			now.live = false;
			deleteKnight(now.row, now.col, now.h, now.w);
			return true;
		}
		
		return false;
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
	
	
	protected int knightDie(int row, int col, int h, int w , int k) {
		for(int i = row; i<row + h; i++) {
			for(int j = col; j<col + w; j++) {
				if(Map[i][j] == 1) {
					k--;
					answer ++;
				}
				if(k == 0) {
					return 0;
				}
			}
		}
		return k;
	}
	
}

public class Main {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		new Solution().solution();
	}

}