package kduraj

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import scala.collection.JavaConversions._
import com.amazonaws.services.s3.model.PutObjectRequest

import sys.process._
import java.io.File

/**
  * AWS S3 Examples: 
  * aws s3 ls s3://kduraj/geoip2/
  * aws s3 rm s3://kduraj/geoip2/GeoLite2-City-20171107.mmdb
  * curl -SsL http://kduraj/download/geoip/database/GeoLite2-City.tar.gz | tar -zxvf -
  */
object UpdateGeoIPDatabase {

    val AWS_ACCESS_KEY = "<access_key>"
    val AWS_SECRET_KEY = "<secret_key>"

    val bucketName = "geoip-bucket"
    // val fileToUpload = new File("/tmp/GeoLite2-City_20171107/GeoLite2-City.mmdb")

    val localDirectory = "/tmp"

    def main(args: Array[String]) {

        println("-" * 100)
        println("Updating GeoIP Database")
        println("-" * 100)

        "curl -O http://geolite.maxmind.com/download/geoip/database/GeoLite2-City.tar.gz" !

        val result1 = s"tar xzvf GeoLite2-City.tar.gz -C $localDirectory" !!
        val version = result1.substring(14,22)
        
        println("-" * 100)
        print(result1)
        println("-" * 100)

        val result2 = s"ls -l $localDirectory/GeoLite2-City_$version" !!
        val fileToUpload = new File(s"/tmp/GeoLite2-City_$version/GeoLite2-City.mmdb")

        print(result2)
        println("-" * 100)

        upload_geoip_database(s"geoip2/GeoLite2-City-$version.mmdb", fileToUpload)

    }

    /**
      * List S3 bucket
      */
    private def list_s3_bucket = {

        val credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)
        val s3client = new AmazonS3Client(credentials)

        // s3client.createBucket(bucketName)
        // List names in the bucket
        for (bucket <- s3client.listBuckets) {
            System.out.println("\t- " + bucket.getName)
        }

    }

    /**
      * Upload GeoIP Database
      * @param dataFile
      * @return
      */
    private def upload_geoip_database(dataFile: String, fileToUpload: File) = {

        val credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)
        val s3client = new AmazonS3Client(credentials)

        // Upload File to S3
        s3client.putObject(bucketName, dataFile, fileToUpload)
    }

}

