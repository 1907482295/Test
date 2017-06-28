import java.util.ArrayList; 
import java.util.List; 
import java.util.Collections;
public class Test  
{  
	public class EdgePoint implements Comparable<EdgePoint>{
		int edge = 0;
	    int left_edge_num = 0;
	    int right_edge_num = 0;
	    int before_left_edge_sum = 0;
	    int before_right_edge_sum = 0;
	    public String toString(){
            return "edge="+edge + "{"+before_left_edge_sum + ","+ before_right_edge_sum
            +"," + left_edge_num +"," + right_edge_num + "}";
	    }
	    public int compareTo(EdgePoint arg0) {
            return this.edge - arg0.edge;
        }
	}
	public List  mEdgePointList = new ArrayList<EdgePoint>();

    public void storeToList(int start[],int end[]){
    	for(int i=0, size=start.length; i<size;i++) {
    		EdgePoint edgePoint = new EdgePoint();
    		edgePoint.edge = start[i];
    		edgePoint.left_edge_num = 1;
    		mEdgePointList.add(edgePoint);
    	}
    	for(int i=0, size=end.length; i<size;i++) {
    		EdgePoint edgePoint = new EdgePoint();
    		edgePoint.edge = end[i];
    		edgePoint.right_edge_num = 1;
    		mEdgePointList.add(edgePoint);
    	}
    }
    public void mergeListDuplicate(){
	    List  tempPointList = new ArrayList<EdgePoint>();
	    tempPointList.add(mEdgePointList.get(0));
    	for(int i=1;i<mEdgePointList.size();i++) {
    		EdgePoint ep = (EdgePoint)mEdgePointList.get(i);
    		EdgePoint epLast = (EdgePoint)tempPointList.get(tempPointList.size()-1);    		
    		if(ep.edge == epLast.edge) {
    			epLast.left_edge_num+=ep.left_edge_num;
    			epLast.right_edge_num+=ep.right_edge_num;
    		} else {
    			ep.before_left_edge_sum = epLast.before_left_edge_sum + epLast.left_edge_num;
    			ep.before_right_edge_sum = epLast.before_right_edge_sum + epLast.right_edge_num;
    			tempPointList.add(ep);
    		}
    	}
    	mEdgePointList.clear();
    	mEdgePointList = tempPointList;
    }
    public int search(int edgeTarget) {  
        int minIndex = 0;  
        int maxIndex = mEdgePointList.size()-1;  
        while(minIndex <= maxIndex) {  
            int midIndex = (minIndex + maxIndex)/2;  
            EdgePoint midValue = (EdgePoint)mEdgePointList.get(midIndex);
            int edge = midValue.edge;
            if( edgeTarget == edge) {              	
            	int num = midValue.before_left_edge_sum - midValue.before_right_edge_sum + midValue.left_edge_num
            	          - midValue.right_edge_num;
                return num;
            }else if(edgeTarget < edge) {  
                maxIndex = midIndex -1;  
            }else {  
                minIndex = midIndex + 1;  
            }  
        }  
        if(maxIndex < 0) {
        	return 0;
        }
        if(minIndex >= mEdgePointList.size()) {
        	return 0;
        }
        EdgePoint value = (EdgePoint)mEdgePointList.get(maxIndex);
        int num = value.before_left_edge_sum + value.left_edge_num - value.before_right_edge_sum - value.right_edge_num;
        return num;  
    }
    public void getTaskNumber(int number_of_tasks_running[],int query[]){
    	for(int i=0,size=query.length;i<size;i++) {
    		int num = search(query[i]);
            number_of_tasks_running[i] = num;
    	}
    }
    public void number_of_tasks_running(int number_of_tasks_running[],
    	int start[],int end[], int n, int query[], int m){
        storeToList(start, end);
        Collections.sort(mEdgePointList);
        mergeListDuplicate();
        getTaskNumber(number_of_tasks_running, query);
    }
    public static void main(String[] args) {  
        int start[] = new int[]{0,5,2};
        int end[] = new int[]{4,7,8};
        int query[] = new int[]{1,9,4,3};
        int number_of_tasks_running[] = new int[query.length];
        new Test().number_of_tasks_running(number_of_tasks_running,
         	start, end, start.length, query, query.length);
        System.out.print("[");
        for(int i=0;i<number_of_tasks_running.length;i++) {
            System.out.print(number_of_tasks_running[i]);
            if(i < number_of_tasks_running.length-1) {
                System.out.print(",");
            }
        }
        System.out.println("]");
    }  
}  