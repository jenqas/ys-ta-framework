Feature: As a user I want to get ability to create Paste Bin with syntax highlighting

  Background:
    Given the user opens Paste Bin website
    And the user scrolls down homepage

  @regression
  Scenario Outline: Created with syntax paste bin details should consist of appropriate data
    When the user enters paste code "<code>"
    And the user enters paste expiration "<pasteExpiration>"
    And the user enters paste name "<pasteName>"
    And the user enters paste syntax highlight "<syntaxHighlight>"
    And the user clicks create button
    Then browser's title should contain paste name "<pasteName>"
    And saved code should be equal to input code "<code>"
    And saved code should have syntax highlight "<syntaxHighlight>"
    Examples:
      | code | pasteExpiration | pasteName | syntaxHighlight |
      | git config --global user.name  \"New Sheriff in Town\"\ngit reset $(git commit-tree HEAD^{tree} -m \"Legacy code\"\ngit push origin master --force | 10 Minutes | how to gain dominance among developers | Bash |
      | private static final int WAIT_TIMEOUT = 15; | 1 Hour | javacode | Java |