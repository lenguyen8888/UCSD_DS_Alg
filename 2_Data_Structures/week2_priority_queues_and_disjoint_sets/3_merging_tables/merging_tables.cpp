#include <cstdio>
#include <cstdlib>
#include <vector>
#include <algorithm>
#include <iostream>

using std::cin;
using std::cout;
using std::endl;
using std::max;
using std::vector;

struct DisjointSetsElement {
	int size, parent, rank;

	DisjointSetsElement(int size = 0, int parent = -1, int rank = 0) :
		size(size), parent(parent), rank(rank) {}
};

struct DisjointSets {
	int size;
	int max_table_size;
	vector <DisjointSetsElement> sets;

	DisjointSets(int size) : size(size), max_table_size(0), sets(size) {
		for (int i = 0; i < size; i++)
			sets[i].parent = i;
	}

	//int getParent(int table) {
	//	// find parent and compress path
	//}

	//void merge(int destination, int source) {
	//	int realDestination = getParent(destination);
	//	int realSource = getParent(source);
	//	if (realDestination != realSource) {
	//		// merge two components
	//		// use union by rank heuristic
	//		// update max_table_size
	//	}
	//}

	int getParent(int table) {
		// find parent and compress path
		if (table != sets[table].parent)
			sets[table].parent = getParent(sets[table].parent);
		return sets[table].parent;
	}

	void merge(int destination, int source) {
		int realDestination = getParent(destination);
		int realSource = getParent(source);
		if (realDestination != realSource) {
			// merge two components
			// use union by rank heuristic
			// update max_table_size
			auto& src = sets[realSource];
			auto& dst = sets[realDestination];
			if (src.rank > dst.rank) {
				dst.parent = realSource;
				src.size += dst.size;
				dst.size = 0;
				max_table_size = max(max_table_size, src.size);
			}
			else {
				src.parent = realDestination;
				dst.size += src.size;
				src.size = 0;
				max_table_size = max(max_table_size, dst.size);
				if (dst.rank == src.rank)
					dst.rank++;
			}
		}
	}
};

int main() {
	int n, m;
	cin >> n >> m;

	DisjointSets tables(n);
	for (auto& table : tables.sets) {
		cin >> table.size;
		tables.max_table_size = max(tables.max_table_size, table.size);
	}

	for (int i = 0; i < m; i++) {
		int destination, source;
		cin >> destination >> source;
		--destination;
		--source;

		tables.merge(destination, source);
		cout << tables.max_table_size << endl;
	}

	return 0;
}
