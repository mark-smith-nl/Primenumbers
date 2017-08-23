# Primenumbers (by M. Smith)
A prime number (or a prime) is a natural number greater than 1 that has no positive divisors other than 1 and itself.
The fist prime number is two. All other primenumbers are odd numbers.

The used algorithm is based on the principle that a number is a prime number if no prime divisors can be found that are smaller or equal as its root. Given an ordered list of primenumbers on the interval [2..ceiling], a number can be efficiently checked to be a prime by dividing it by each given primenumber in the list and checking whether the remainder is zero.
