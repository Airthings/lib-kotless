package io.kotless

/** Types of supported AWS resources */
enum class AwsResource(
    val prefix: String, val glob: (region: String, account: String) -> String,
    val read: Set<String>, val write: Set<String>
) {
    S3(
        "s3",
        glob = { _, _ -> "arn:aws:s3::" },
        read = setOf("Get*", "Describe*", "List*", "AbortMultipartUpload"),
        write = setOf("Create*", "Delete*", "ObjectOwnerOverrideToBucketOwner", "Put*", "Replicate*", "Update*")
    ),
    SSM(
        "ssm",
        glob = { region, account -> "arn:aws:ssm:$region:$account" },
        read = setOf("GetParameter", "GetParameters", "GetParameterHistory", "GetParametersByPath", "DescribeParameters"),
        write = setOf("DeleteParameter", "DeleteParameters", "PutParameter")
    ),
    DynamoDB(
        "dynamodb",
        glob = { region, account -> "arn:aws:dynamodb:$region:$account" },
        read = setOf("BatchGetItem", "GetItem", "TransactGetItems", "Query", "Scan", "Describe*", "List*"),
        write = setOf("BatchWriteItem", "PutItem", "TransactWriteItems", "Create*", "Delete*", "Restore*", "Update*", "TagResource", "UntagResource")
    ),
    DynamoDBIndex(
        "dynamodb",
        glob = { region, account -> "arn:aws:dynamodb:$region:$account" },
        read = setOf("Query", "Scan"),
        write = setOf()
    ),
    SQSQueue(
        "sqs",
        glob = { region, account -> "arn:aws:sqs:$region:$account" },
        read = setOf("Get*", "List*", "ReceiveMessage"),
        write = setOf("DeleteMessage", "PurgeQueue", "SendMessage")
    ),
    CloudWatchLogs(
        "logs",
        glob = { region, account -> "arn:aws:logs:$region:$account" },
        read = setOf(
            "GetLogEvents",
            "GetLogRecord",
            "GetLogGroupFields",
            "GetQueryResults",
            "DescribeLogGroups",
            "DescribeLogStreams",
            "DescribeMetricFilters"
        ),
        write = setOf("CreateLogGroup", "DeleteLogGroup", "CreateLogStream", "DeleteLogStream", "PutLogEvents", "DeleteMetricFilter", "PutMetricFilter")
    );

    companion object {
        /** Resources that can be created during local start */
        
        val forLocalStart = setOf(S3, SSM, DynamoDB)
    }
}

/** Types of supported Azure resources */
enum class AzureResource(
    val prefix: String, val read: Set<String>, val write: Set<String>, val readWrite: Set<String>
) {
    Resource(
        "resource",
        read = setOf("Reader"),
        write = setOf("Contributor"),
        readWrite = setOf("Contributor")
    ),
    StorageAccount(
        "storage-account",
        read = setOf("Reader"),
        write = setOf("Contributor"),
        readWrite = setOf("Contributor")
    );
}

/** Level of access -- Read/Write/ReadWrite */
enum class PermissionLevel {
    Read,
    Write,
    ReadWrite;
}
