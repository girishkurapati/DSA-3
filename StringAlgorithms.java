package dsa;
import java.util.*;
public final class StringAlgorithms{
 private StringAlgorithms(){}
 public static List<Integer> kmp(String t,String p){List<Integer>o=new ArrayList<>();if(p.isEmpty()||p.length()>t.length())return o;int[]l=lps(p);for(int i=0,j=0;i<t.length();){if(t.charAt(i)==p.charAt(j)){i++;j++;if(j==p.length()){o.add(i-j);j=l[j-1];}}else if(j>0)j=l[j-1];else i++;}return o;}
 private static int[]lps(String p){int[]l=new int[p.length()];for(int i=1,len=0;i<p.length();){if(p.charAt(i)==p.charAt(len))l[i++]=++len;else if(len>0)len=l[len-1];else i++;}return l;}
 public static List<Integer> z(String t,String p){List<Integer>o=new ArrayList<>();if(p.isEmpty()||p.length()>t.length())return o;String s=p+'\0'+t;int[]z=new int[s.length()];for(int i=1,l=0,r=0;i<s.length();i++){if(i<=r)z[i]=Math.min(r-i+1,z[i-l]);while(i+z[i]<s.length()&&s.charAt(z[i])==s.charAt(i+z[i]))z[i]++;if(i+z[i]-1>r){l=i;r=i+z[i]-1;}if(z[i]==p.length()&&i>p.length())o.add(i-p.length()-1);}return o;}
 public static List<Integer> rk(String t,String p){List<Integer>o=new ArrayList<>();if(p.isEmpty()||p.length()>t.length())return o;long mod=1_000_000_007L,base=911382323L,ph=0,th=0,pow=1;int m=p.length();for(int i=0;i<m;i++){ph=(ph*base+p.charAt(i))%mod;th=(th*base+t.charAt(i))%mod;if(i<m-1)pow=pow*base%mod;}for(int i=0;i<=t.length()-m;i++){if(ph==th&&t.regionMatches(i,p,0,m))o.add(i);if(i<t.length()-m){th=(th-t.charAt(i)*pow)%mod;if(th<0)th+=mod;th=(th*base+t.charAt(i+m))%mod;}}return o;}
}