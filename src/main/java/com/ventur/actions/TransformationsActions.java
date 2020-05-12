package com.ventur.actions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import scala.Tuple2;

/**
 * 1.1 count() 1.2 collect() 1.3 take(n) 1.4 top(n) 1.5 countByValue() 1.6
 * reduce() 1.7 fold() 1.8 aggregate() 1.9 foreach() 1.10 saveAsTextFile()
 *
 */
public class TransformationsActions {

	private static List<String> ArrayList;

	public static void main(String[] args) {

		SparkConf sparkConf = new SparkConf();

		sparkConf.setMaster("local[1]");

		sparkConf.setAppName("Transformations And Actions Spark Example");

		JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

		JavaRDD<String> likes = javaSparkContext.parallelize(Arrays.asList("Java"));

		JavaRDD<String> learn = javaSparkContext.parallelize(Arrays.asList("Spark", "Scala"));

		JavaRDD<String> likeToLearn = likes.union(learn);

		List<String> result = likeToLearn.collect();

		// Learning::2
		System.out.println("Learning::" + learn.count());

		// Prints I like [Java, Spark, Scala]
		System.out.println("I like " + result.toString());

		String[] newlyLearningSkills = { "Elastic Search", "Spring Boot" };

		JavaRDD<String> learningSkills = learn.union(javaSparkContext.parallelize(Arrays.asList(newlyLearningSkills)));

		// learningSkills::[Spark, Scala, Elastic Search, Spring Boot]
		System.out.print("learningSkills::" + learningSkills.collect().toString());

		List<String> learning4Skills = learningSkills.take(4);

		// Learning 4 Skills::[Spark, Scala, Elastic Search, Spring Boot]
		System.out.println("Learning 4 Skills::" + learning4Skills.toString());

		List<String> learningTop2Skills = learningSkills.top(2);

		// Learning top 2 Skills::[Spring Boot, Spark]
		System.out.println("Learning top 2 Skills::" + learningTop2Skills.toString());

		Map<String, Long> skillCountMap = learningSkills.countByValue();

		for (Map.Entry<String, Long> entry : skillCountMap.entrySet()) {
			System.out.println("key::" + entry.getKey() + "\t" + "value:" + entry.getValue());
		}

		Tuple2<String, Integer> javaTestSection1 = new Tuple2<String, Integer>("Java", 45);
		Tuple2<String, Integer> javaTestSection2 = new Tuple2<String, Integer>("Java", 40);
		Tuple2<String, Integer> sparkTestSection1 = new Tuple2<String, Integer>("Spark", 46);
		Tuple2<String, Integer> sparkTestSection2 = new Tuple2<String, Integer>("Spark", 42);

		List<Tuple2<String, Integer>> testsList = new ArrayList<Tuple2<String, Integer>>();
		testsList.add(javaTestSection1);
		testsList.add(javaTestSection2);
		testsList.add(sparkTestSection1);
		testsList.add(sparkTestSection2);

		JavaPairRDD<String, Integer> examMarks = javaSparkContext.parallelizePairs(testsList);
		examMarks.foreach(tuple -> System.out.println("key:" + tuple._1 + "\t value:" + tuple._2));

		learningTop2Skills.forEach(element -> System.out.println("e:" + element));

		learningSkills.foreach(element -> System.out.println(element));

		javaSparkContext.stop();

		javaSparkContext.close();

	}

}
