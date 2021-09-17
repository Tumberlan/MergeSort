public class QuickSort {

    public void qSort(int[] array, int first, int last){
        if(first < last) {
            int left = first;
            int right = last;
            int middle = array[(left + right) / 2];
            while (left <= right) {
                while (array[left] < middle) {
                    left++;
                }
                while (array[right] > middle) {
                    right--;
                }
                if (left <= right) {
                    int tmp = array[left];
                    array[left] = array[right];
                    array[right] = tmp;
                    left++;
                    right--;
                }
            }
            qSort(array, first, right);
            qSort(array, left, last);
        }

    }
}
