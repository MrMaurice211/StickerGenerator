package me.mrmaurice.sg;

public class PackSetting {

	private String name;
	private String publisher;
	private String image_data_version;
	private boolean avoid_cache;
	private String publisher_email;
	private String publisher_website;
	private String privacy_policy_website;
	private String license_agreement_website;
	
	public void apply(StickerPack pack) {
		pack.setName(name);
		pack.setPublisher(publisher);
		pack.setImage_data_version(image_data_version);
		pack.setAvoid_cache(avoid_cache);
		pack.setPublisher_email(publisher_email);
		pack.setPublisher_website(publisher_website);
		pack.setPrivacy_policy_website(privacy_policy_website);
		pack.setLicense_agreement_website(license_agreement_website);
	}
	
}
