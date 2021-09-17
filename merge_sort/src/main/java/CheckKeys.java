public class CheckKeys {


    public void check(String str, boolean[] params, int[] Idxs) {
        boolean checker = false;
        if (str.equals("-a")) {
            params[0] = true;
            checker = true;
        }
        if(str.equals("-d")){
            params[0] = false;
            checker = true;
        }
        if(str.equals("-s")){
            params[1] = false;
            checker = true;
        }
        if(str.equals("-i")){
            params[1] = true;
            checker = true;
        }
        if(checker){
            Idxs[0]++;
            Idxs[1]++;
        }

    }
}
