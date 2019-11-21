import re

def classifyStrings(s):
    x = re.search(r'[aeiou]{3}|[bcdfghjklmnpqrstvwxyz]{5}',s)
    if x:
        return "bad"
    y = s.find("?")
    if y > -1:
        vowel = classifyStrings(s.replace("?", "a", 1))
        conso = classifyStrings(s.replace("?", "b", 1))
        if vowel == conso:
            return conso
        return "mixed"
    return "good"
