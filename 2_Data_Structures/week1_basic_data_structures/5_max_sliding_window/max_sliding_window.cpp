#include <iostream>
#include <vector>
#include <queue>

using std::cin;
using std::cout;
using std::vector;
using std::max;

void max_sliding_window(vector<int> const& A, int w) {
	std::deque<int> dq;
	for (size_t i = 0; i < A.size(); ++i) {
		while (!dq.empty() && A[i] >= A[dq.back()]) {
			dq.pop_back();
		}
		dq.push_back(i);
		if ((i >= w) && !dq.empty() && (dq.front() == (i - w)))
			dq.pop_front();
		if (i >= w - 1)
			cout << A[dq.front()] << " ";
	}
	return;
}

void max_sliding_window_naive(vector<int> const& A, int w) {
	for (size_t i = 0; i < A.size() - w + 1; ++i) {
		int window_max = A.at(i);
		for (size_t j = i + 1; j < i + w; ++j)
			window_max = max(window_max, A.at(j));

		cout << window_max << " ";
	}
}


int main() {
	int n = 0;
	cin >> n;

	vector<int> A(n);
	for (size_t i = 0; i < n; ++i)
		cin >> A.at(i);

	int w = 0;
	cin >> w;

	max_sliding_window(A, w);

	return 0;
}
