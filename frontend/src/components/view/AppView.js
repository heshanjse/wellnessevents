

import HrView from './HrView';
import VendorView from './vendor/VendorView';


function AppContent({ accessToken, userRole, userCompany }) {
  return (
    <div>
      {userRole === 'HR_ADMIN' || userRole === 'HR_USER' ? (
        <HrView accessToken={accessToken} userRole={userRole} userCompany={userCompany} />
      ) : (
        <VendorView accessToken={accessToken} userRole={userRole} userCompany={userCompany} />
      )}
    </div>
  );
}

export default AppContent;
