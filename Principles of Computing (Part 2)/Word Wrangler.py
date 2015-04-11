"""
Student code for Word Wrangler game
"""

import urllib2
import codeskulptor
import poc_wrangler_provided as provided

WORDFILE = "assets_scrabble_words3.txt"


# Functions to manipulate ordered word lists

def remove_duplicates(list1):
    """
    Eliminate duplicates in a sorted list.

    Returns a new sorted list with the same elements in list1, but
    with no duplicates.

    This function can be iterative.
    """
    list2 = []
    for idx in list1:
        if not list2.count(idx): 
            list2.append(idx) 
    return list2

def intersect(list1, list2):
    """
    Compute the intersection of two sorted lists.

    Returns a new sorted list containing only elements that are in
    both list1 and list2.

    This function can be iterative.
    """
    return remove_duplicates([item for item in list1 + list2 if item in list1 and item in list2])

# Functions to perform merge sort

def merge(list1, list2):
    """
    Merge two sorted lists.

    Returns a new sorted list containing all of the elements that
    are in either list1 and list2.

    This function can be iterative.
    """   
    result = []
    left_idx, right_idx = 0, 0
    while left_idx < len(list1) and right_idx < len(list2):
        if list1[left_idx] <= list2[right_idx]:
            result.append(list1[left_idx])
            left_idx += 1
        else:
            result.append(list2[right_idx])
            right_idx += 1
    if list1:
        result.extend(list1[left_idx:])
    if list2:
        result.extend(list2[right_idx:])
    return result
                
def merge_sort(list1):
    """
    Sort the elements of list1.

    Return a new sorted list with the same elements as list1.

    This function should be recursive.
    """
    if len(list1) <= 1:
        return list1
 
    middle = len(list1) // 2
    left = list1[:middle]
    right = list1[middle:]
 
    left = merge_sort(left)
    right = merge_sort(right)
    return list(merge(left, right))

# Function to generate all strings for the word wrangler game

def gen_all_strings(word):
    """
    Generate all strings that can be composed from the letters in word
    in any order.

    Returns a list of all strings that can be formed from the letters
    in word.

    This function should be recursive.
    """
    if len(word) == 0:
        return [""]
    elif len(word) == 1:
        return [""] + [word]
    else:
        first_letter = word[0]
        rest_letters = word[1: ]
        rest_strings = gen_all_strings(rest_letters)
        first_letter_strings = []
        for rest_word in rest_strings:
            for idx in range(len(rest_word)+1):
                string = ""
                if idx == 0:
                    string = first_letter + rest_word
                elif idx == len(rest_word):
                    string = rest_word + first_letter
                else:
                    string = rest_word[ :idx] + first_letter + rest_word[idx: ]
                first_letter_strings.append(string)             
    return first_letter_strings + rest_strings

# Function to load words from a file

def load_words(filename):
    """
    Load word list from the file named filename.

    Returns a list of strings.
    """ 
    data = urllib2.urlopen(codeskulptor.file2url(filename))
    return [line[:-1] for line in data.readlines()]

def run():
    """
    Run game.
    """
    words = load_words(WORDFILE)
    wrangler = provided.WordWrangler(words, remove_duplicates, 
                                     intersect, merge_sort, 
                                     gen_all_strings)
    provided.run_game(wrangler)

# Uncomment when you are ready to try the game
run()