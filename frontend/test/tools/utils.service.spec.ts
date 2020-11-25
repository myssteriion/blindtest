import { UtilsService } from "../../src/app/tools/utils.service";
import { CommonUtilsService } from "myssteriion-utils";

describe("UtilsService", () => {
	
	let service: UtilsService;
	
	
	
	beforeAll(() => {
		service = new UtilsService( new CommonUtilsService() );
	});
	
	
});
