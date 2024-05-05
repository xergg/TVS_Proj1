package ap;
 
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

@Test 
public class TestQuestion {
  private Question q;

  //Test Case 1
  @Test
  public void testQuestionWith0Topics() {
    // Arrange
    Question q = new Question("testbody", Arrays.asList("choice1", "choice2", "choice3"),
                                1, "test to", 2);

    // Act & Assert
    assertThrows(InvalidOperationException.class, () -> {q.remove("test to");});
    assertEquals(q.getChoices(), Arrays.asList("choice1", "choice2", "choice3"));
    assertEquals(q.getTopics(), Arrays.asList("test to"));
    assertEquals(q.getWeight(), 2);
  }

  //Test Case 2
  @Test
  public void testQuestionWith1Topic() {
    // Arrange
    Question q = new Question("verycoolbody", Arrays.asList("st", "nd", "rd", "th"),
                                4, "testtopc", 3);

    // Act
    result = q.grade(3);

    // Assert
    assertEquals(q.getTopics(), Arrays.asList("testtopc"));
    assertEquals(q.getWeight(), 3);
    assertEquals(q.getChoices(), Arrays.asList("st", "nd", "rd", "th"));
  }

  //Test Case 3
  @Test
  public void testQuestionWith5Topics() {
    // Arrange
    Question q = new Question("thirdbod", Arrays.asList("um", "dois", "tres", "quatro", "cinco"),
                                3, "test12345", 4);

    // Act
    q.add("Question9");
    q.add("With12345");
    q.add("512345678");
    q.add("Topics789");
    result = q.grade(1);

    // Assert
    assertEquals(q.getWeight(), 4);
    assertEquals(q.getTopics(), Arrays.asList("test12345", "Question9", "With12345", "512345678", "Topics789"));
    assertEquals(q.getChoices(), Arrays.asList("um", "dois", "tres", "quatro", "cinco"));
  }

  //Test Case 4
  @Test
  public void testQuestionWith6Topics() {
    // Arrange
    Question q = new Question("notverynicebody", Arrays.asList("1", "2", "3", "4", "5", "6"),
                                2, "1234567890", 5);

    // Act
    q.add("tenchars10");
    q.add("testtestte");
    q.remove("testtestte");
    q.add("0987654321");
    q.add("abcdeabcde");
    q.add("zxzxzxzxzx");

    // Act & Assert
    assertThrows(InvalidOperationException.class, () -> {q.add("toomanytop");});
    result = q.grade(2);
    assertEquals(q.getTopics(), Arrays.asList("1234567890", "tenchars10", "0987654321", "abcdeabcde", "zxzxzxzxzx"));
    assertEquals(q.getWeight(), 5);
    assertEquals(q.getChoices(), Arrays.asList("1", "2", "3", "4", "5", "6"));
  }

  //Test Case 5 & 8
  //These cases consider 3 topics, but since the class fails to be created, only the first is specified
  @DataProvider
  private Object[][] computeDataForInvalidWeights() {
    return new Object[][] {
      {"notoriginalbody", Arrays.asList("a", "b", "c"), 1, "asdfghj", 0},
      {"originalbody", Arrays.asList("a", "b", "c", "d", "e", "f"), 6, "poiuytr", 16}};
  }

  @Test (expectedExceptions = InvalidOperationException.class, dataProvider = "computeDataForInvalidWeights")
  public void testQuestionWithInvalidWeights(String body, List<String> choices, int correctChoice, 
                                              String topic1, int weight) {
    // Act
    Question q = new Question(body, choices, correctChoice, topic1, weight);
  }

  //Test Case 6 & 7
  @DataProvider
  private Object[][] computeDataForValidWeights() {
    return new Object[][] {
      {"yesbody", Arrays.asList("z", "y", "x", "w"), 3, "qazwsxed", "plokijuh", 1},
      {"nobody", Arrays.asList("rato", "espada", "serpente", "tartaruga", "oceano"), 5, 
       "rtyfghvbn", "qazwsrfvc", 15}};
  }

  @Test (dataProvider = "computeDataForValidWeights")
  public void testQuestionWithValidWeights(String body, List<String> choices, int correctChoice, 
                                              String topic1, String topic2, int weight) {
    // Arrange & Act
    Question q = new Question(body, choices, correctChoice, topic1, weight);
    q.add(topic2);
    result = q.grade(4);

    // Assert
    assertEquals(q.getWeight(), weight);
    assertEquals(q.getChoices(), choices);
    assertEquals(q.getTopics(), Arrays.asList(topic1, topic2));
  }
}