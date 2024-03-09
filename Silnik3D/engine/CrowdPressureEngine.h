#include "Types.h"
#include <vector>

class CrowdPressureEngine {


public:

    virtual const std::vector<const Types::Agent &> &getAgents() const = 0;

    virtual const Types::Map &getMap() const = 0;

    virtual void initialize(const Types::Configuration &configuration) = 0;

};