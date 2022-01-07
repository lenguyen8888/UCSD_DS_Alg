call_track = 0
DEBUG = True
def while_cond(border, p, i):
    global call_track
    call_track += 1
    if DEBUG:
        print("while_cond", call_track, "i", i, "p[i]", p[i], \
        " border", border, "p[border]", p[border])
    return border > 0 and p[i] != p[border]
def computePrefix(p):
    if DEBUG:
        print("p", p)
    s = [0] * len(p)
    border = 0
    call_track = 1;
    for i in range(1, len(p)):
        #while (border > 0) and (p[i] != p[border]):
        while while_cond(border, p, i):
            border = s[border - 1]
        if p[i] == p[border]:
            border += 1
        else:
            border = 0
        s[i] = border
    return s


if __name__ == '__main__':
  text = "ACATACATACACA"
  s = computePrefix(text)
  print(call_track)
  print(s)
