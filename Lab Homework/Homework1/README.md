# <center>Zero.java

## Explanation

This Java code defines a class called "Zero" with several methods that perform operations on an array of integers.The
main method initializes an array with a sequence of integers and then uses the other methods to perform a calculation.

The findSmallestNonzero method iterates through the array and finds the smallest non-zero element in the array. It
returns this element.

The whetherPositive method iterates through the array and checks whether there is any positive element in the array. It
returns a boolean value indicating whether there are any positive elements in the array.

The subtractToGetNewArray method creates a new array with the same length as the input array and populates it with the
result of subtracting the smallest non-zero element from each element of the input array.

The main method read the given array in the txt file "array.txt" and checks whether there are any positive elements in
the array. If
there are no positive elements in the array, it prints 0.
Otherwise, it repeatedly calls the subtractToGetNewArray method on the array until there are no more positive elements
in the array. Each time it calls this method, it increments a counter variable called "ans".Finally, it prints the value
of "ans".

In summary, this Java code calculates the number of times the smallest non-zero element of an array can be subtracted
from all the positive elements in the array until there are no more positive elements left.

Note that randomArrayGenerate.java is used for generating a random array for test, and the array generated is saved in
the file "array.txt".

## Time complexity

The main function has a loop that iterates over the length of the input array. Inside this loop, the whetherPositive
function is called, which has a time complexity of O(n) as it iterates over the input array. Then, the
subtractToGetNewArray function is called, which also has a time complexity of O(n^2) because it calls the
findSmallestNonzero function (O(n)) inside another loop that iterates over the input array (O(n)).
So, the time complexity of the code is:

O(n) for the outer loop in the main function

O(n) for the whetherPositive function

O(n^2) for the subtractToGetNewArray function

Multiplying these complexities together, we get O(n^3) as the overall time complexity of the code.

# <center>Matrix.java

## Explanation

In the main method, create a Scanner object to read input from the user.
Read two integers n and m from the user, representing the dimensions of the matrix.
Initialize a 2D array matrix of size n x m.
Initialize variables count, rowStart, rowEnd, colStart, and colEnd to control the spiral traversal.
Use a while loop to traverse the matrix in a spiral order and fill it with numbers from 1 to n * m.
Finally, output the elements in the matrix one by one.

## Time complexity

The time complexity of the code is O(n * m), where n is the number of rows and m is the number of columns in the matrix.
This is because the code iterates through each element of the matrix exactly once while filling it with numbers in a
spiral order. The nested loops inside the while loop are responsible for traversing the matrix, and each iteration of
these loops processes one element of the matrix. Since there are n * m elements in the matrix, the overall time
complexity is O(n * m).