import json
import boto3
from datetime import datetime

s3 = boto3.client('s3')


def binarySearch(logs,curr_time,delta,low=0,high=0):
    high = len(logs) -1
    while(low<=high):
        mid = (high + low) // 2
        # get the time by splitting the log file with space and delim
        # index 0 is the time
        mid_time = datetime.strptime(logs[mid].split(" ")[0], "%H:%M:%S.%f")
       
       # delta provided is in minutes
       # if difference of user provided time 
        if (abs(curr_time - mid_time).total_seconds()/60) <= delta:
            return True
        elif mid_time <= curr_time :
            low = mid + 1
        else:
            high = mid - 1
      
    return False #not found
    
def lambda_handler(event, context):

    # Extract the query string parameter sent by the client first.. these are required 

    time = event['queryStringParameters']['time']
    delta =event['queryStringParameters']['delta']
    
    # get the s3 object
    get_s3_object =  boto3.resource('s3').Object("outputlogs-cs-441","LogFileGenerator.2021-11-05.log")
    
    # read in the log file
    logs = get_s3_object.get()['Body'].read().decode('utf-8').splitlines()
  
    
    # convert the string to datetime 
    # 20:16:24.584
    curr_time = datetime.strptime(time, "%H:%M:%S.%f")
    
    if binarySearch(logs,curr_time,int(delta)) == False:
         return {
            'statusCode': 400,
            'body': json.dumps('No Logs found within provided time and delta')
        }
    else: # logs found
        return {
            'statusCode': 200,
            'body': json.dumps('Logs found within provided time and delta!')
        }
    
        
    
