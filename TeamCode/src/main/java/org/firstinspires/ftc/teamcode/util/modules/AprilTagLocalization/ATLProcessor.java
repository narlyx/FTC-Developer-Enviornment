package org.firstinspires.ftc.teamcode.util.modules.AprilTagLocalization;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.Dictionary;
import java.util.Hashtable;

import dev.narlyx.ftc.tweetybird.TweetyBirdProcessor;

/**
 * Processor for odometry via AprilTags
 */
public class ATLProcessor {

    /**
     * Reference to the current LinearOpMode
     */
    protected LinearOpMode opMode;

    /**
     * Reference to the current AprilTagProcessor
     */
    protected AprilTagProcessor aprilTagProcessor;

    /**
     * Reference to the current TweetyBirdProcessor
     */
    protected TweetyBirdProcessor tweetyBirdProcessor;

    Dictionary<Integer,Tag> tags;

    /**
     * Internal method that is called right after the builder is "built"
     */
    private void runtime() {
        ATLWorker workerThread = new ATLWorker(this);
        workerThread.start();
    }

    /**
     * Constructor
     * @param builder The builder method MUST be used to start the class
     */
    public ATLProcessor(Builder builder) {
        //Pulling builder configurations
        this.opMode = builder.opMode;
        this.aprilTagProcessor = builder.aprilTagProcessor;
        this.tweetyBirdProcessor = builder.tweetyBirdProcessor;
        this.tags = builder.tags;

        //Ensure all required imports have been set
        if ( this.opMode==null || this.aprilTagProcessor==null || this.tweetyBirdProcessor==null || this.tags.size()==0 ) {
            //return;
        }

        //Starting
        runtime();
    }

    /**
     * Used to create/start this class
     */
    public static class Builder {
        //Variables
        private LinearOpMode opMode = null;
        public ATLProcessor.Builder setOpMode(LinearOpMode opMode) {
            this.opMode = opMode;
            return this;
        }

        private AprilTagProcessor aprilTagProcessor = null;
        public ATLProcessor.Builder setAprilTagProcessor(AprilTagProcessor aprilTagProcessor) {
            this.aprilTagProcessor = aprilTagProcessor;
            return this;
        }

        private TweetyBirdProcessor tweetyBirdProcessor  = null;
        public ATLProcessor.Builder setTweetyBirdProcessor(TweetyBirdProcessor tweetyBirdProcessor) {
            this.tweetyBirdProcessor = tweetyBirdProcessor;
            return this;
        }


        private Dictionary<Integer,Tag> tags = new Hashtable<>();
        public ATLProcessor.Builder addTag(int id, double x, double y, double z) {
            tags.put(id,new Tag(x,y,z));
            return this;
        }


        public ATLProcessor build() {
            return new ATLProcessor(this);
        }
    }
}