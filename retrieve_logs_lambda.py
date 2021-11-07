import json
import boto3
from datetime import datetime
import re
s3 = boto3.client('s3')

def binary_search(logs,curr_time,delta,low=0,high=0):
    high = len(logs) -1
    while(low<=high):
        mid_idx = (high + low) // 2
        # get the time by splitting the log file with space and delim
        # index 0 is the time
        mid_time = datetime.strptime(logs[mid_idx].split(" ")[0], "%H:%M:%S.%f")
       
       # delta provided is in minutes
       # if difference of user provided time 
        if (abs(curr_time - mid_time).total_seconds()/60) <= delta:
            return mid_idx
        elif mid_time <= curr_time :
            low = mid + 1
        else:
            high = mid - 1
      
    return -1 #not found

def lower_upper_index(lowerIdx,curr_time,logs,delta):
    # get the time at the index where input time was found
    mid_time = datetime.strptime(logs[lowerIdx].split(" ")[0], "%H:%M:%S.%f")
    upperIndex = lowerIdx
    mid_time2 = datetime.strptime(logs[upperIndex].split(" ")[0], "%H:%M:%S.%f")
    while(abs(mid_time - curr_time).total_seconds()/60) <= delta :
        lowerIdx -=1
        
    while(abs(mid_time2 - curr_time).total_seconds()/60) <= delta :
        upperIndex+=1
    
    return lowerIdx,upperIndex
    
def retrieve(pattern,logs, delta,curr_time,low,high):
   
    matched_messages = []
    while (low<=high):
        
        if re.search(pattern,logs[low].split()[5]) != None:
            matched_messages.append(logs[mid_idx].split()[5])
            low+=1;
            
    return matched_messages
     
    
    
def lambda_handler(event, context):
    
   
    time = event['queryStringParameters']['time']
    delta =event['queryStringParameters']['delta']
    pattern =event['queryStringParameters']['pattern']
     # get the s3 object
    get_s3_object =  boto3.resource('s3').Object("outputlogs-cs-441","LogFileGenerator.2021-11-05.log")
    
    # read in the log file
    logs = get_s3_object.get()['Body'].read().decode('utf-8').splitlines()
    
    curr_time = datetime.strptime(time, "%H:%M:%S.%f")
    
    idx =  binary_search(logs,curr_time,int(delta))
    
    if idx != -1: # if log exists
        low,high = lower_upper_index(idx,curr_time,logs,delta)
        matched_messages = retrieve(pattern,logs,delta,curr_time,low,high)
        
    if len(matched_messages) > 0:
        return {
             'statusCode': 200,
             'body': json.dumps('Found matched strings!')
        }
    else :
        return {
        'statusCode': 400,
        'body': json.dumps('Matched strings not found')
    }

        
        
    
