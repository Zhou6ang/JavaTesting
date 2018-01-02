package org.example.app;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Hello world!
 *
 */
public class App
{
	public static class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

		private IntWritable count = new IntWritable(1);
		private Text text = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			System.out.println("Line:"+key.get()+", "+value);
			String str[] = value.toString().split(" ");
			if(str.length != 0){
				for (String string : str) {
					text.set(string);
					context.write(text, count);
				}
			}
			
			
		}
		
	}
	
	public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

		private IntWritable result = new IntWritable();
		
		@Override
		protected void reduce(Text arg0, Iterable<IntWritable> arg1,
				Reducer<Text, IntWritable, Text, IntWritable>.Context arg2) throws IOException, InterruptedException {
			
			int sum = 0;
			for (IntWritable e : arg1) {
				sum += e.get();
			}
			result.set(sum);
			arg2.write(arg0, result);
		}
		
	}
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "My first Job");
        job.setJarByClass(App.class);
        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);
        job.setCombinerClass(MyReducer.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileInputFormat.setInputDirRecursive(job, true);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);
        
    }
}


