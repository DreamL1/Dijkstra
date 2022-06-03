
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author chp
 *
 * @param <V>
 * 
 *            ģ��һ������ͼ���ڽӱ�ʵ��
 */
public class UndiretedListGraph<V> implements Graph<V> {
	Vertex<V>[] vertexList; // �ڽ� ����
	private int edges; // �ߵ�����
	private int vertices; // ���������

	public UndiretedListGraph(int size) {
		vertexList = new Vertex[size];
		this.edges = 0;
		this.vertices = 0;
	}

	class Vertex<V> {
		V data; // ����ֵ
		LinkedList<Integer> adj; // �ڽӱ�

		Vertex(V data) {
			this.data = data; // ����ֵ
			this.adj = new LinkedList<>(); // ÿ��������ڽӵ㹹�ɵ��ڽӱ�
		}
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
		this.vertexList[this.vertices++] = new Vertex<>(v);

	}

	@Override
	public void addEdge(V from, V to) {
		// ����һ����
		int i = this.getPosition(from);
		int j = this.getPosition(to);

		this.vertexList[i].adj.add(j);
		this.vertexList[j].adj.add(i);
		this.edges++;
	}

	private int getPosition(V v) {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.vertices; i++) {
			if (this.vertexList[i].data.equals(v)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void addEdge(V from, V to, int weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEdge(V from, V to) {
		// ɾ��һ����
		int i = this.getPosition(from);
		int j = this.getPosition(to);

		this.vertexList[i].adj.remove(new Integer(j)); // ˼����
		this.vertexList[j].adj.remove(new Integer(i)); // ɾ����Ӧ����һ�����󣬶����Ǹ�������index������ɾ��
		this.edges++;

	}

	@Override
	public void removeVertex(V v) {
		// ɾ��һ������
		/*
		 * 1. ��ѯ����v�����index 2. ���±ߵĸ��� 3. ɾ�������ڽӵ��Ӧ��index 4. ��������ɾ����ǰ�Ķ��� 5. ���¶���ĸ��� 6.
		 * ���������ڽӱ������ֵ�������д���index��ֵȫ����
		 */
		int index = this.getPosition(v);

		this.edges = this.edges - this.degree(v);

		for (int i : this.vertexList[index].adj) {
			this.vertexList[i].adj.remove(new Integer(index));
		}

		for (int i = index; i < this.vertices - 1; i++) {
			this.vertexList[i] = this.vertexList[i + 1];
		}
		this.vertices--;
		for (int i = 0; i < this.vertices; i++) {
			LinkedList<Integer> list = vertexList[i].adj;
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j) > index) {
					list.set(j, list.get(j) - 1);
				}

			}
		}

	}

	@Override
	public void displayGraph() {
		// ��ʾ��ǰͼ
		System.out.println("����һ��ʹ���ڽӱ�洢��ͼ��");
		for (int i = 0; i < this.vertices; i++) {
			System.out.print("���㣺" + this.vertexList[i].data);
			System.out.println(",�ڽӱ�" + this.vertexList[i].adj);
		}

	}

	@Override
	public int degree(V v) {
		// TODO Auto-generated method stub
		return this.vertexList[getPosition(v)].adj.size();
	}

	@Override
	public void dfs() {
		// ������ȱ���
		boolean[] beTraversed = new boolean[this.vertices];
		// ���涥��ı���״̬��Ĭ��Ϊfalse
		beTraversed[0] = true;
		System.out.print("������ȱ��������");
		System.out.print(this.vertexList[0].data);
		this.dfs(0, beTraversed);
		System.out.println();
	}

	private void dfs(int i, boolean[] beTraversed) {
		// �ӵ�i�����㿪ʼ������ȱ���
		for (int j : this.vertexList[i].adj) {
			if (!beTraversed[j]) {
				beTraversed[j] = true;
				System.out.print(this.vertexList[j].data);
				this.dfs(j, beTraversed);
			}
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
			System.out.print(this.vertexList[index].data);
			for (int i : this.vertexList[index].adj) {
				// ����δ�����ʹ����ڽӵ����
				if (!beTraversed[i]) {
					beTraversed[i] = true;
					queue.offer(i);
				}
			}
		}
		System.out.println();

	}



}
