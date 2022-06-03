import java.io.IOError;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * 
 * @author chp
 *
 * @param <V>
 * 
 *            ģ��ʵ��һ������ͼ���ڽӾ���
 */

public class UndiretedMatrixGrpah<V> implements Graph<V> {
	private List<V> vertexList; // ���涥����Ϣ
	private int[][] edgeMatrix; // �����ڽӾ���
	private int edges; // �ߵ�����
	private int vertices; // ��������
	private int size; // ��ģ

	public UndiretedMatrixGrpah(int size) {
		this.size = size;
		vertexList = new ArrayList<>();
		edgeMatrix = new int[size][size];
		this.edges = 0;
		this.vertices = 0;
	}

	@Override
	public int edgesSize() {
		// TODO Auto-generated method stub
		return this.edges;
	}

	@Override
	public int verticesSize() {
		// TODO Auto-generated method stub
		return this.vertices;
	}

	@Override
	public void addVertex(V v) {
		// ����һ������
		this.vertexList.add(v);
		this.vertices++;
	}

	@Override
	public void addEdge(V from, V to) {
		// TODO Auto-generated method stub
		this.addEdge(from, to, 1);
	}

	@Override
	public void addEdge(V from, V to, int weight) {
		// TODO Auto-generated method stub
		int i = this.vertexList.indexOf(from);
		int j = this.vertexList.indexOf(to);
		this.edgeMatrix[i][j] = weight;
		this.edgeMatrix[j][i] = weight;
		this.edges++;

	}

	@Override
	public void removeEdge(V from, V to) {
		// ɾ��һ����
		int i = this.vertexList.indexOf(from);
		int j = this.vertexList.indexOf(to);
		this.edgeMatrix[i][j] = 0;
		this.edgeMatrix[j][i] = 0;
		this.edges--;
	}

	@Override
	public void removeVertex(V v) {
		// ɾ��һ������
		int index = this.vertexList.indexOf(v); // ��ɾ����������

		for (int i = 0; i < this.vertices; i++) {
			if (this.edgeMatrix[index][i] != 0) { // ��ʾindex��i֮����һ����
				this.edges--;
			}
		}

		// index�����������ǰ��һ��
		for (int i = index; i < this.vertices - 1; i++) {
			for (int j = 0; j < this.vertices; j++) {
				this.edgeMatrix[i][j] = this.edgeMatrix[i + 1][j];
			}
		}

		// index�����������ǰ��һ��
		for (int i = 0; i < this.vertices; i++) {
			for (int j = index; j < this.vertices - 1; j++) {
				this.edgeMatrix[i][j] = this.edgeMatrix[i][j + 1];
			}
		}

		this.vertexList.remove(index);
		this.vertices--;

	}

	@Override
	public void displayGraph() {
		// ��ʾ��ǰͼ
		System.out.println("����һ������ͼ���ڽӾ��󣩣�");
		System.out.println("���㣺" + this.vertexList);
		System.out.println("ͼ���ڽӾ����ǣ�");
		for (int i = 0; i < this.vertices; i++) {
			for (int j = 0; j < this.vertices; j++) {
				System.out.print(this.edgeMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	@Override
	public int degree(V v) {
		// ����ͼ�Ķ���v�Ķ�

		int index = this.vertexList.indexOf(v); // ��ȡ��������
		int count = 0;
		for (int i = 0; i < this.vertices; i++) {
			if (this.edgeMatrix[index][i] != 0) { // ��ʾindex��i֮����һ����
				count++;
			}
		}
		return count;
	}

	@Override
	public void dfs() {
		// ������ȱ���
		boolean[] beTraversed = new boolean[this.vertices];
		// ���涥��ı���״̬��Ĭ��Ϊfalse
		beTraversed[0] = true;
		System.out.print("������ȱ��������");
		System.out.print(this.vertexList.get(0));
		this.dfs(0, 1, beTraversed);
		System.out.println();

	}

	private void dfs(int x, int y, boolean[] beTraversed) {
		// ����x�ĵ�y���ڽӵ�
		while (y < this.vertices) {
			if (this.edgeMatrix[x][y] != 0 && !beTraversed[y]) {
				beTraversed[y] = true;
				System.out.print(this.vertexList.get(y));
				this.dfs(y, 0, beTraversed); // ��y�ĵ�0���ڽӵ㿪ʼ������ȱ���
			}
			y++;
		}

	}

	@Override
	public void bfs() {
		// ������ȱ���
		boolean[] beTraversed = new boolean[this.vertices];
		// ���涥��ı���״̬��Ĭ��Ϊfalse
		beTraversed[0] = true;
		Queue<Integer> queue = new LinkedList<>();
		beTraversed[0] = true;
		queue.offer(0);

		System.out.print("������ȱ�����");
		while (!queue.isEmpty()) {
			int index = queue.poll();
			System.out.print(this.vertexList.get(index));
			for (int i = 0; i < this.vertices; i++) {
				if (this.edgeMatrix[index][i] != 0 && !beTraversed[i]) {
					// �ҳ�����û�з��ʹ����ڽӵ�
					beTraversed[i] = true;
					queue.offer(i);
				}
			}
		}
		System.out.println();

	}

	/*
	 * ʹ��prim�㷨������С������
	 */
	public void prim() {
		ArrayList<Integer> listU = new ArrayList<>(); // �洢�Ѿ��������Ķ��㼯��
		listU.add(0);
		ArrayList<Integer> listV = new ArrayList<>(); // ��û�������������Ķ��㼯��
		for (int i = 1; i < this.vertices; i++) {
			listV.add(i);
		}
		int miniWeight;
		int miniFrom = -1;
		int miniTo = -1;
		while (!listV.isEmpty()) {
			// �ҳ���������֮�����бߵ���Сֵ
			miniWeight = Integer.MAX_VALUE; // ����һ������ʱ������ÿ����������֮���Ȩֵ��Сֵ
			for (int i : listU) {
				for (int j : listV) {
					if (this.edgeMatrix[i][j] != 0 && this.edgeMatrix[i][j] < miniWeight) {
						miniWeight = this.edgeMatrix[i][j];
						miniFrom = i;
						miniTo = j;
					}
				}
			}
			listU.add(miniTo);
			listV.remove(new Integer(miniTo));

			System.out.println("Edge: <" + this.vertexList.get(miniFrom) + ", " + this.vertexList.get(miniTo) + "> : "
					+ miniWeight);

		}
	}
	/**
	 * 	�ڲ��ࣺ��
	 */
	class Edge<V>{
		V from;			//��ʼ����
		V to ;			//��������
		int weigth;	//�ߵ�Ȩֵ
		public Edge(V from, V to, int weigth) {
			super();
			this.from = from;
			this.to = to;
			this.weigth = weigth;
		}
		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weigth=" + weigth + "]";
		}
		
	}
	
	public void kruskal(){
		ArrayList<Edge> edgelist = new ArrayList<>(); //���б�
		int[] list = new int[this.vertices];		//list[i]�����˶���i������ͨ������������Ķ������
		int count = 0 ;			//��С�������ߵ�����
		for(int i = 0;i<this.vertices;i++) {
			for(int  j = i+1 ; j<this.vertices;j++) {
				if(this.edgeMatrix[i][j] != 0) {
					edgelist.add( new Edge<V>(this.vertexList.get(i), this.vertexList.get(j), this.edgeMatrix[i][j]));
				}
			}
		}
		Collections.sort(edgelist, new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				// TODO Auto-generated method stub
				return o1.weigth - o2.weigth;
			}
		});
		System.out.println(edgelist);
		
		System.out.println("��С�������ߣ�");
		
		for(Edge edge:edgelist) {					//���δ���Ȩֵ��С�ı�
			int from = this.vertexList.indexOf(edge.from);   //����ʼ��������
			int to = this.vertexList.indexOf(edge.to);		//�߽�����������
			
			int  m = this.getEnd(from,list);		//���ҵ�ǰ����������ͨ�������ֵ���Ķ���
			int  n = this.getEnd(to,list);			//���ҵ�ǰ����������ͨ�������ֵ���Ķ���
			
			if(m != n) {				//���뵱ǰ�����ߣ������ɻ�·
				list[m] = n;			//�޸�ǰ��һ����ͨ������󶥵�ָ�����һ����󶥵����
				System.out.println(edge);
				count++;
				if(count == this.vertices-1) {
					break;
				}
			}
			
		}
	}

	private int getEnd(int x,int[] list) {
		while(list[x] != 0) {
			x = list[x];
		}
		
		return x;
	}
	
}
