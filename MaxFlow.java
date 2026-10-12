package dsa;
import java.util.*;
/** Edmonds-Karp maximum flow; BFS gives the augmenting path sequence. */
public final class MaxFlow {
 private MaxFlow(){}
 public record Step(List<Integer> path,long bottleneck,long total){}
 public record Result(long maxFlow,List<Step> steps,long[][] residual){}
 public static Result solve(long[][] cap,int src,int sink){
  int n=cap.length; if(n==0||src<0||sink<0||src>=n||sink>=n||src==sink) throw new IllegalArgumentException("Invalid source/sink.");
  long[][] r=new long[n][n];for(int i=0;i<n;i++){if(cap[i].length!=n)throw new IllegalArgumentException("Capacity matrix must be square.");r[i]=cap[i].clone();}
  long total=0;List<Step> steps=new ArrayList<>();
  while(true){int[] par=new int[n];Arrays.fill(par,-1);par[src]=src;long[] b=new long[n];b[src]=Long.MAX_VALUE;ArrayDeque<Integer>q=new ArrayDeque<>();q.add(src);
   while(!q.isEmpty()&&par[sink]<0){int u=q.remove();for(int v=0;v<n;v++)if(par[v]<0&&r[u][v]>0){par[v]=u;b[v]=Math.min(b[u],r[u][v]);q.add(v);if(v==sink)break;}}
   if(par[sink]<0)break;long add=b[sink];List<Integer> path=new ArrayList<>();for(int v=sink;;v=par[v]){path.add(0,v);if(v==src)break;}total+=add;steps.add(new Step(path,add,total));
   for(int v=sink;v!=src;v=par[v]){int u=par[v];r[u][v]-=add;r[v][u]+=add;}
  }
  return new Result(total,steps,r);
 }
}