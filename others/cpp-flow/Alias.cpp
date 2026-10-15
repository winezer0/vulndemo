class AliasBox { public: int value; };
using BoxName = AliasBox;
using AnotherName = BoxName;
int aliasRead(AnotherName& box) { return box.value; }
class RightBox { public: int value; };
int localFirst() { using Alias = AliasBox; Alias box; return box.value; }
int localSecond() { using Alias = RightBox; Alias box; return box.value; }
