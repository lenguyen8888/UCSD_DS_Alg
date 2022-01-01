#include <iostream>
#include <vector>
#include <algorithm>
#include <cassert>

//int MaxPairwiseProduct(const std::vector<int>& numbers) {
//    int max_product = 0;
//    int n = numbers.size();
//
//    for (int first = 0; first < n; ++first) {
//        for (int second = first + 1; second < n; ++second) {
//            max_product = std::max(max_product,
//                numbers[first] * numbers[second]);
//        }
//    }
//
//    return max_product;
//}

int64_t MaxPairwiseProduct(const std::vector<int>& numbers) {
	int max_product = 0;
	int n = numbers.size();
	assert(numbers.size() >= 2);
	int firstMax = std::max(numbers[0], numbers[1]);
	int secondMax = std::min(numbers[0], numbers[1]);
	for (int i = 2;i < numbers.size();++i) {
		secondMax = std::max(secondMax, std::min(firstMax, numbers[i]));
		firstMax = std::max(firstMax, numbers[i]);
	}

	return ((int64_t)firstMax) * ((int64_t)secondMax);
}


int main() {
	int n;
	std::cin >> n;
	std::vector<int> numbers(n);
	for (int i = 0; i < n; ++i) {
		std::cin >> numbers[i];
	}

	std::cout << MaxPairwiseProduct(numbers) << "\n";
	return 0;
}
