#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <android/log.h>
#include "socketutil.h"
#include <sys/socket.h>  
#include <netinet/in.h>  
#include <stdlib.h>  
#include <android/log.h>  

#define MAXSIZE 4096  
struct sockaddr_in sock_add;  
struct sockaddr_in server_addr;  
int sockfd = 0;  
  
void release(){  
   close(sockfd);  
}  
  
const char* connectRemote(const char* addr,const int port) { 
     sockfd = socket(AF_INET, SOCK_STREAM, 0);    //ipv4,TCP数据连接  
     LOGI("step1");  
     if (sockfd < 0) {  
        return "socket error";  
     }  
    //remote server address  
     bzero(&server_addr,sizeof(server_addr));  
     server_addr.sin_family = AF_INET;  
     server_addr.sin_port = htons(port);  
     LOGI("step2");  
     if( inet_pton(AF_INET, addr, &server_addr.sin_addr) < 0){    //set ip address  
       LOGI("address error");
       return "address error";  
     }  
     socklen_t server_addr_length = sizeof(server_addr);  
     int connfd = connect(sockfd, (struct sockaddr*)&server_addr, sizeof(server_addr)); //连接到服务器  
     LOGI("step3");  
     if (connfd < 0) {  
        return "connect error";  
     }  
     // send data  
     char *msg = "i send a data to server. \n";  
     int sdf = send(sockfd, msg , strlen(msg), 0); //发送一行数据到服务器  
     LOGI("step4.");  
     char buff[4096];  
     int n = recv(sockfd, buff, MAXSIZE ,0);  //这地方应该需要循环获取数据，目前服务器只响应了一条很短的字符串。  
     buff[n] = 0;  
     LOGI("step5.");  
     return buff;  
}  