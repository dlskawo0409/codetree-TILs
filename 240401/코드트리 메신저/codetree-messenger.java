// package codeTree;
import java.util.*;
import java.io.*;

//class Node{
////	int num; 
////	Node parent;
////	Node  left;
////	Node right;
//	int parent;
//	boolean alarm = true;
//	int power;
//	
//	Node(int parent, int power){
//		this.parent = parent;
//		this.power = power;
//	}
//}

class Solution{
	int N;
	int Q;
//	Node[] nodeList;
	int[] parentList;
	int[] powerList;
	boolean[] alarmList;
	
	public void solution() throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q =Integer.parseInt(st.nextToken());
//		nodeList = new Node[N+1];
//		nodeList[0] = new Node(-1, -1);
//		nodeList[0].alarm = false;
		
		parentList= new int[N+1];
		powerList = new int[N+1];
		alarmList = new boolean[N+1];
		
		
		for(int q = 0; q<Q; q++) {
			st = new StringTokenizer(br.readLine());
			int cmd = Integer.parseInt(st.nextToken());
			
			
			if(cmd == 100) { // 사내 메신저 준비
//				int[] tempParent = new int[N+1];
//				int[] tempPower = new int[N+1];
				
				for(int n = 1; n<=N; n++) {
//					tempParent[n] = Integer.parseInt(st.nextToken());
					parentList[n] = Integer.parseInt(st.nextToken());
					alarmList[n] = true;
				}
				for(int n = 1; n<=N; n++) {
//					tempPower[n] = Integer.parseInt(st.nextToken());
					powerList[n] = Integer.parseInt(st.nextToken());
				}
				
//				for(int n = 1; n<=N; n++) {
//					nodeList[n] = new Node(tempParent[n], tempPower[n]);
//				}

				
			}
			else if(cmd == 200) { //알림망 설정
				
				int node = Integer.parseInt(st.nextToken());
//				nodeList[node].alarm = false;
				alarmList[node] = !alarmList[node];
				
			}
			else if(cmd == 300) { // 권한 세기 변경
				
				int node = Integer.parseInt(st.nextToken());
				int tempPower = Integer.parseInt(st.nextToken());
//				nodeList[node].power = tempPower;
				powerList[node] = tempPower;
//				System.out.println("node : "+ node +" tempPoewer : "+ tempPower);
			}
			else if(cmd == 400) { // 부모 채팅방 교환
				int c1 = Integer.parseInt(st.nextToken());
				int c2 = Integer.parseInt(st.nextToken());
				
				int temp = parentList[c1];
				parentList[c1] = parentList[c2];
				parentList[c2] = temp;
				
//				temp = powerList[c1];
//				powerList[c1] = powerList[c2];
//				powerList[c2] = temp;
//				
				
//				boolean tempAlarm = alarmList[c1];
//				alarmList[c1] = alarmList[c2];
//				alarmList[c2] = tempAlarm;
				
				
				
			}
			else if(cmd == 500) { // 알림을 받을 수 있는 채팅방 수 조회
				
				int[] tempList = new int[N+1];
				int goal = Integer.parseInt(st.nextToken());
				int count = 0;
				for(int n = 1; n<=N; n++) {
					int now = n;
//					while(alarmList[n] && (parentList[n] != 0 && parentList[n] != goal)) {
//						
//					}
					int power = powerList[n];
					for(int i = 0; i<powerList[n]; i++) {
						if(parentList[now] == goal && alarmList[now]) { // 부모가 goal 이고 전달 가능하면 
							count++;
							tempList[now] = i+1;
		
							break;
						}
						else if(parentList[now] == 0) {// 마지막
							break;
						}
						else if(tempList[parentList[now]] > power || !alarmList[now]) { //선배가 불가능
							tempList[now] = Integer.MAX_VALUE;
							break;
						}
						else if(tempList[parentList[now]] != 0 && tempList[parentList[now]] + 1 <= power) { // 가능
							count++;
							tempList[now] = tempList[parentList[now]] + 1;
							break;
						}
						now = parentList[now];
						power--;
						
					}
//					System.out.print(tempList[n]+" ");
				}
//				System.out.println();
				System.out.println(count);
//				System.out.println("parentList");
//				for(int i = 1; i<=N; i++) {
//					System.out.print(parentList[i]+" ");
//				}
//				System.out.println();
//				
//				System.out.println("powerList");
//				for(int i = 1; i<=N; i++) {
//					System.out.print(powerList[i]+" ");
//				}
//				System.out.println();
			}

		}
	}
}

public class Main {
	public static void main(String[] args) throws Exception{
		new Solution().solution();
	}
}