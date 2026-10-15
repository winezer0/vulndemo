class DynamicService { create(): void {} }
class DynamicController {
  service: DynamicService;
  outer(): void {
    function nested(): void { this.service.create(); }
    const arrow = () => this.service.create();
    nested(); arrow();
  }
}
