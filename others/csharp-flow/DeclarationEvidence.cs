using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

class EvidenceBox {
 public int Echo<T>(int item) { return item; }
}
class EvidenceService {
 public int RunEvidence(int input) {
  var box = new EvidenceBox();
  int output = box.Echo<int>(input);
  return output;
 }
 public int SlotsEvidence(ref int first, out int second, in int third, params int[] remaining) {
  second = first;
  return remaining[0] + third;
 }
 [HttpGet("/evidence,a", Name="literal")]
 public void LiteralEvidence() {}
 [HttpGet(Paths.Unknown)]
 public void DynamicEvidence() {}
}
class EvidenceDatabase : DbContext {
 public DbSet<EvidenceEntity> Entities { get; set; }
 public DbSetCache<EvidenceFake> Cache { get; set; }
 public List<DbSet<EvidenceFake>> Nested { get; set; }
}
class EvidenceEntity {}
class EvidenceFake {}
