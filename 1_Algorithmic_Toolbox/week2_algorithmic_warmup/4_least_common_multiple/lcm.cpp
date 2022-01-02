#include <iostream>

long long lcm_naive(int a, int b) {
  for (long l = 1; l <= (long long) a * b; ++l)
    if (l % a == 0 && l % b == 0)
      return l;

  return (long long) a * b;
}

int gcd(int a, int b) {
    if (a > b)
        return gcd(b, a);
    if (a % b == 0)
        return b;
    return gcd(b % a, a);
}

long long lcm(int a, int b) {
    return (long long)a / gcd(a,b) * b;
}

int main() {
  int a, b;
  std::cin >> a >> b;
  std::cout << lcm_naive(a, b) << std::endl;
  return 0;
}
