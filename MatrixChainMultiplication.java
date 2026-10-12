package dsa;
/** Interval-DP Matrix Chain Multiplication used by the project. */
public final class MatrixChainMultiplication {
 private MatrixChainMultiplication(){}
 public record Result(long[][] cost,int[][] split,long minCost,String order){}
 public static Result solve(int[] p){
  if(p==null||p.length<3) throw new IllegalArgumentException("At least two matrices are required.");
  int n=p.length-1; long[][] m=new long[n+1][n+1]; int[][] s=new int[n+1][n+1];
  for(int len=2;len<=n;len++) for(int i=1;i<=n-len+1;i++){
   int j=i+len-1; m[i][j]=Long.MAX_VALUE/4;
   for(int k=i;k<j;k++){
    long q=m[i][k]+m[k+1][j]+(long)p[i-1]*p[k]*p[j];
    if(q<m[i][j]){m[i][j]=q;s[i][j]=k;}
   }
  }
  return new Result(m,s,m[1][n],order(s,1,n));
 }
 private static String order(int[][]s,int i,int j){if(i==j)return "M"+i;int k=s[i][j];return "("+order(s,i,k)+" × "+order(s,k+1,j)+")";}
}