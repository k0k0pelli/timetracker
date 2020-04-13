package id.meier.timetracking.reporting;
class ReportDefinition {
		private String name;
		private String id;
		
		ReportDefinition(String name, String id) {
			this.name = name;
			this.id = id;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public String toString() {
			return name;
		}
	}