package patterns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Optimizing Box Weights
 * 
 * @author Khusein @ 2022
 * 
 * An Associate has a set of items that need to
 * be packed into two boxes. Given an integer array of the item
 * weights (arr) to be packed, divide the item weights into two
 * subsets, A and B, for packing into the associated boxes, while
 * respecting the following conditions:
 * The intersection of A and B is null.
 * • The union A and B is equal to the original array.
 * • The number of elements in subset A is minimal.
 * • The sum of A's weights is greater than the sum of B's weights.
 * Return the subset A in increasing order where the sum of A's
 * weights is greater than the sum of B's weights. If more than one
 * subset A exists, return the one with the maximal total weight.
 *
 * Example:
 * n= 5
 * arr = (3, 7, 5, 6, 2]
 * The 2 subsets in arr that satisfy the conditions for A are [5, 7)
 * and [6, 7]:
 * • A is minimal (size 2)
 * • Sum(A) = (5 + 7) = 12 > Sum(B) = (2 + 3 + 6) = 11
 * • Sum(A) = (6 + 7) = 13 > Sum(B) = (2 + 3 + 5) = 10
 * • The intersection of A and B is null and their union is equal to arr.
 * • The subset A where the sum of its weight is maximal is [6, 7].
 */

public class SMW {
    public static int getSum(List<Integer> nums) {
        int sum = 0;
        for (int val: nums) {
            sum += val;
        }
        return sum;
    }

    public static int getSum(List<Integer> nums, Integer nextValue) {
        int sum = 0;
        for (int val: nums) {
            sum += val;
        }
        return sum + nextValue;
    }

    public static List<Integer> sort(List<Integer> nums) {
    	Integer[] dest = nums.toArray(Integer[]::new);
    	Arrays.sort(dest);
        return List.of(dest);
    }
    
    /*
     * Complete the 'minimalHeaviestSetA' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static List<Integer> minimalHeaviestSetA(List<Integer> arr) {
    	// Write your code here
        System.out.println("Source: " + arr.toString());

        List<Integer> dest = SMW.sort(arr);
        int n = dest.size();

    	if (n < 3) {
    		return dest;
    	}

        List<Integer> setA = new ArrayList<>();
        List<Integer> setB = new ArrayList<>();

        // Init
        Integer leftValue = dest.get(0);
        setB.add(leftValue);
        Integer rightValue = dest.get(n - 1);
        setA.add(rightValue);
        
        int idx = 0;
        int lastIdx = n - 2;

        while (++idx <= lastIdx) {

        	leftValue = dest.get(idx);
            
        	// If needs to give 2 elements for the SetA 
        	//if (idx == lastIdx && setA.size() == 1 ) {
            //    setA.add(leftValue);
        	//	break;
        	//}
            
            setB.add(leftValue);

            if (SMW.getSum(setB) > SMW.getSum(setA) && lastIdx > idx) {
                rightValue = dest.get(lastIdx);
                setA.add(rightValue);
                lastIdx--;
            }
        }
        
        System.out.println("SetA: " + setA.toString());
        System.out.println("SetB: " + setB.toString());

        return SMW.sort(setA);
    }

}
